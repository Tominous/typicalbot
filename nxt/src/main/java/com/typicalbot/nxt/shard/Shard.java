/*
 * Copyright 2019 Bryan Pikaard, Nicholas Sylke and the TypicalBot contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.typicalbot.nxt.shard;

import com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats;
import com.sedmelluq.discord.lavaplayer.jdaudp.NativeAudioSendFactory;
import com.sedmelluq.discord.lavaplayer.player.AudioConfiguration;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.twitch.TwitchStreamAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.typicalbot.command.CommandManager;
import com.typicalbot.nxt.audio.GuildMusicManager;
import com.typicalbot.nxt.command.core.*;
import com.typicalbot.nxt.command.miscellaneous.SayCommand;
import com.typicalbot.nxt.command.moderation.*;
import com.typicalbot.nxt.command.music.*;
import com.typicalbot.nxt.command.system.EvalCommand;
import com.typicalbot.nxt.command.system.TestCommand;
import com.typicalbot.nxt.command.utility.*;
import com.typicalbot.nxt.command.webhook.WebhookCommand;
import com.typicalbot.nxt.config.Config;
import com.typicalbot.nxt.listener.GuildListener;
import com.typicalbot.nxt.listener.ReadyListener;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// TODO(nsylke): Documentation
public class Shard {
    private static Shard singleton;

    private final String clientId;
    private final int shardId;
    private final int shardTotal;

    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManager;

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
    private CommandManager commandManager = new CommandManager();
    private JDA instance;

    public Shard(String token) {
        this(token, null, 0, 1);
    }

    public Shard(String token, String clientId, int shardId, int shardTotal) {
        singleton = this;

        this.clientId = clientId;
        this.shardId = shardId;
        this.shardTotal = shardTotal;

        try {
            JDABuilder builder = new JDABuilder(AccountType.BOT)
                .setToken(token)
                .setAutoReconnect(true)
                .setActivity(Activity.playing("Client Started"))
                .setStatus(OnlineStatus.IDLE)
                .setBulkDeleteSplittingEnabled(true)
                .setEnableShutdownHook(true)
                .setContextEnabled(true)
                .setDisabledCacheFlags(EnumSet.of(CacheFlag.ACTIVITY))
                .useSharding(shardId, shardTotal);

            if (!System.getProperty("os.arch").equalsIgnoreCase("arm") && !System.getProperty("os.arch").equalsIgnoreCase("arm-linux")) {
                builder.setAudioSendFactory(new NativeAudioSendFactory());
            }

            builder.addEventListeners(
                new ReadyListener(),
                new GuildListener()
            );

            this.instance = builder.build();

            this.commandManager.registerCommands(
                // Core
                new ChangelogCommand(),
                new CommandsCommand(),
                new DonateCommand(),
                new HelpCommand(),
                new InviteCommand(),
                new PingCommand(),
                new PrimeCommand(),
                new ShardsCommand(),
                new StatisticsCommand(),
                new SupportCommand(),
                new UptimeCommand(),
                new VersionCommand(),
                new VoteCommand(),

                // Miscellaneous
                new SayCommand(),

                // Moderation
                new AdcheckCommand(),
                new AliasCommand(),
                new AnnounceCommand(),
                new BanCommand(),
                new ChannelsCommand(),
                new DeafenCommand(),
                new EmojisCommand(),
                new FilterCommand(),
                new GiveCommand(),
                new IgnoreCommand(),
                new KickCommand(),
                new ModulesCommand(),
                new MuteCommand(),
                new PCSCommand(),
                new PurgeCommand(),
                new ReasonCommand(),
                new RolesCommand(),
                new SettingsCommand(),
                new SlowmodeCommand(),
                new SoftbanCommand(),
                new TakeCommand(),
                new UnbanCommand(),
                new UndeafenCommand(),
                new UnignoreCommand(),
                new UnmuteCommand(),
                new VoicekickCommand(),
                new VoicemoveCommand(),
                new VoicemuteCommand(),
                new VoiceunmuteCommand(),
                new WarnCommand(),

                // Music
                new CurrentCommand(),
                new LyricsCommand(),
                new PauseCommand(),
                new PlayCommand(),
                new QueueCommand(),
                new RepeatCommand(),
                new ResumeCommand(),
                new SeekCommand(),
                new ShuffleCommand(),
                new SkipCommand(),
                new StopCommand(),
                new UnqueueCommand(),
                new VolumeCommand(),

                // System
                new EvalCommand(),
                new TestCommand(),

                // Utility
                new AvatarCommand(),
                new BotsCommand(),
                new ChannelCommand(),
                new DiscriminatorCommand(),
                new HexCommand(),
                new LevelCommand(),
                new MessageCommand(),
                new NicknameCommand(),
                new RandomuserCommand(),
                new RoleCommand(),
                new SearchCommand(),
                new ServerCommand(),
                new ServersCommand(),
                new SubscribeCommand(),
                new UnsubscribeCommand(),
                new UserCommand(),

                // Webhook
                new WebhookCommand()
            );

            this.musicManager = new HashMap<>();
            this.playerManager = new DefaultAudioPlayerManager();

            this.playerManager.getConfiguration().setOpusEncodingQuality(AudioConfiguration.OPUS_QUALITY_MAX);
            this.playerManager.getConfiguration().setResamplingQuality(AudioConfiguration.ResamplingQuality.HIGH);
            this.playerManager.getConfiguration().setOutputFormat(StandardAudioDataFormats.DISCORD_OPUS);

            this.playerManager.registerSourceManager(new YoutubeAudioSourceManager());
            this.playerManager.registerSourceManager(new TwitchStreamAudioSourceManager());

            AudioSourceManagers.registerRemoteSources(this.playerManager);
            AudioSourceManagers.registerLocalSource(this.playerManager);

            this.executorService.scheduleAtFixedRate(() -> this.instance.getPresence().setActivity(Activity.playing(Config.getConfig("discord").getString("prefix") + "help | " + ShardManager.getGuildCount() + " Guilds")), 30L, 60L, TimeUnit.SECONDS);
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
    }

    public static Shard getSingleton() {
        return singleton;
    }

    /**
     * Get the client identifier, if exists. Otherwise use internal method to get client identifier.
     *
     * @return the client identifier
     */
    public String getClientId() {
        if (this.clientId == null) {
            return Long.toString(this.instance.getSelfUser().getIdLong());
        }

        return this.clientId;
    }

    /**
     * Get the current shard identifier.
     *
     * @return the shard identifier
     */
    public int getShardId() {
        return this.shardId;
    }

    /**
     * Get the total amount of shards.
     *
     * @return total shards
     */
    public int getShardTotal() {
        return this.shardTotal;
    }

    /**
     * Get the JDA singleton.
     *
     * @return JDA singleton
     */
    public JDA getInstance() {
        return this.instance;
    }

    /**
     * Get the current amount of guilds on this shard.
     *
     * @return Guild size
     */
    public int getGuilds() {
        return this.instance.getGuilds().size();
    }

    public int getChannels() {
        return this.instance.getTextChannels().size() + this.instance.getCategories().size() + this.instance.getVoiceChannels().size();
    }

    /**
     * Get the current amount of users on this shard.
     *
     * @return User size
     */
    public int getUsers() {
        return this.instance.getUsers().size();
    }

    public int getVoiceConnections() {
        return (int) this.instance.getVoiceChannels().stream().filter(v -> v.getMembers().contains(v.getGuild().getSelfMember())).count();
    }

    /**
     * Get the command manager.
     *
     * @return command manager
     */
    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    /**
     * Get the ping of the Discord API.
     *
     * @return Discord API ping
     */
    public long getPing() {
        return this.instance.getGatewayPing();
    }

    public AudioPlayerManager getPlayerManager() {
        return this.playerManager;
    }

    public Map<Long, GuildMusicManager> getMusicManager() {
        return this.musicManager;
    }

    /**
     * Properly shutdown the Discord bot.
     */
    public void shutdown() {
        this.instance.shutdown();
        this.executorService.shutdown();
    }
}
