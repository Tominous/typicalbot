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
package com.typicalbot.shard;

import com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats;
import com.sedmelluq.discord.lavaplayer.jdaudp.NativeAudioSendFactory;
import com.sedmelluq.discord.lavaplayer.player.AudioConfiguration;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.twitch.TwitchStreamAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.typicalbot.audio.GuildMusicManager;
import com.typicalbot.command.CommandManager;
import com.typicalbot.command.core.ChangelogCommand;
import com.typicalbot.command.core.CommandsCommand;
import com.typicalbot.command.core.DonateCommand;
import com.typicalbot.command.core.HelpCommand;
import com.typicalbot.command.core.InviteCommand;
import com.typicalbot.command.core.PingCommand;
import com.typicalbot.command.core.PrimeCommand;
import com.typicalbot.command.core.ShardsCommand;
import com.typicalbot.command.core.StatisticsCommand;
import com.typicalbot.command.core.SupportCommand;
import com.typicalbot.command.core.UptimeCommand;
import com.typicalbot.command.core.VersionCommand;
import com.typicalbot.command.core.VoteCommand;
import com.typicalbot.command.fun.BunnyCommand;
import com.typicalbot.command.fun.CatCommand;
import com.typicalbot.command.fun.ChooseCommand;
import com.typicalbot.command.fun.CookieCommand;
import com.typicalbot.command.fun.DogCommand;
import com.typicalbot.command.fun.EightballCommand;
import com.typicalbot.command.fun.EmojiCommand;
import com.typicalbot.command.fun.EmojifyCommand;
import com.typicalbot.command.fun.FacesCommand;
import com.typicalbot.command.fun.FlipCommand;
import com.typicalbot.command.fun.JokeCommand;
import com.typicalbot.command.fun.LmgtfyCommand;
import com.typicalbot.command.fun.NatoCommand;
import com.typicalbot.command.fun.QuoteCommand;
import com.typicalbot.command.fun.RandomCommand;
import com.typicalbot.command.fun.ReverseCommand;
import com.typicalbot.command.fun.RockpaperscissorsCommand;
import com.typicalbot.command.fun.RollCommand;
import com.typicalbot.command.fun.RomanCommand;
import com.typicalbot.command.fun.ThisorthatCommand;
import com.typicalbot.command.fun.WouldyouratherCommand;
import com.typicalbot.command.fun.YomammaCommand;
import com.typicalbot.command.fun.ZalgolizeCommand;
import com.typicalbot.command.integration.StrawpollCommand;
import com.typicalbot.command.integration.UrbandictionaryCommand;
import com.typicalbot.command.integration.WeatherCommand;
import com.typicalbot.command.integration.WikipediaCommand;
import com.typicalbot.command.integration.XkcdCommand;
import com.typicalbot.command.interaction.HugCommand;
import com.typicalbot.command.interaction.PunchCommand;
import com.typicalbot.command.interaction.ShootCommand;
import com.typicalbot.command.interaction.SlapCommand;
import com.typicalbot.command.interaction.StabCommand;
import com.typicalbot.command.miscellaneous.SayCommand;
import com.typicalbot.command.moderation.AdcheckCommand;
import com.typicalbot.command.moderation.AliasCommand;
import com.typicalbot.command.moderation.AnnounceCommand;
import com.typicalbot.command.moderation.BanCommand;
import com.typicalbot.command.moderation.ChannelsCommand;
import com.typicalbot.command.moderation.DeafenCommand;
import com.typicalbot.command.moderation.EmojisCommand;
import com.typicalbot.command.moderation.FilterCommand;
import com.typicalbot.command.moderation.GiveCommand;
import com.typicalbot.command.moderation.IgnoreCommand;
import com.typicalbot.command.moderation.KickCommand;
import com.typicalbot.command.moderation.MuteCommand;
import com.typicalbot.command.moderation.PCSCommand;
import com.typicalbot.command.moderation.PurgeCommand;
import com.typicalbot.command.moderation.ReasonCommand;
import com.typicalbot.command.moderation.RolesCommand;
import com.typicalbot.command.moderation.SettingsCommand;
import com.typicalbot.command.moderation.SlowmodeCommand;
import com.typicalbot.command.moderation.SoftbanCommand;
import com.typicalbot.command.moderation.TakeCommand;
import com.typicalbot.command.moderation.UnbanCommand;
import com.typicalbot.command.moderation.UndeafenCommand;
import com.typicalbot.command.moderation.UnignoreCommand;
import com.typicalbot.command.moderation.UnmuteCommand;
import com.typicalbot.command.moderation.VoicekickCommand;
import com.typicalbot.command.moderation.VoicemoveCommand;
import com.typicalbot.command.moderation.VoicemuteCommand;
import com.typicalbot.command.moderation.VoiceunmuteCommand;
import com.typicalbot.command.moderation.WarnCommand;
import com.typicalbot.command.music.CurrentCommand;
import com.typicalbot.command.music.LyricsCommand;
import com.typicalbot.command.music.PauseCommand;
import com.typicalbot.command.music.PlayCommand;
import com.typicalbot.command.music.QueueCommand;
import com.typicalbot.command.music.RepeatCommand;
import com.typicalbot.command.music.ResumeCommand;
import com.typicalbot.command.music.SeekCommand;
import com.typicalbot.command.music.ShuffleCommand;
import com.typicalbot.command.music.SkipCommand;
import com.typicalbot.command.music.StopCommand;
import com.typicalbot.command.music.TempoCommand;
import com.typicalbot.command.music.UnqueueCommand;
import com.typicalbot.command.music.VolumeCommand;
import com.typicalbot.command.system.EvalCommand;
import com.typicalbot.command.utility.AvatarCommand;
import com.typicalbot.command.utility.BotsCommand;
import com.typicalbot.command.utility.ChannelCommand;
import com.typicalbot.command.utility.DiscriminatorCommand;
import com.typicalbot.command.utility.HexCommand;
import com.typicalbot.command.utility.LevelCommand;
import com.typicalbot.command.utility.MessageCommand;
import com.typicalbot.command.utility.NicknameCommand;
import com.typicalbot.command.utility.RandomuserCommand;
import com.typicalbot.command.utility.RoleCommand;
import com.typicalbot.command.utility.SearchCommand;
import com.typicalbot.command.utility.ServerCommand;
import com.typicalbot.command.utility.ServersCommand;
import com.typicalbot.command.utility.SubscribeCommand;
import com.typicalbot.command.utility.UnsubscribeCommand;
import com.typicalbot.command.utility.UserCommand;
import com.typicalbot.command.webhook.WebhookCommand;
import com.typicalbot.config.Config;
import com.typicalbot.listener.GuildListener;
import com.typicalbot.listener.ReadyListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.utils.cache.CacheFlag;

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
            this.instance = new JDABuilder(AccountType.BOT)
                .setToken(token)
                .setAutoReconnect(true)
                .setAudioEnabled(true)
                .setGame(Game.playing("Client Started")) // Same as TypicalBot 2.x
                .setStatus(OnlineStatus.IDLE) // Set to IDLE while still loading, change ONLINE when ready
                .setBulkDeleteSplittingEnabled(true)
                .setEnableShutdownHook(true)
                .setDisabledCacheFlags(EnumSet.of(CacheFlag.GAME))
                .setAudioSendFactory(new NativeAudioSendFactory())
                .useSharding(shardId, shardTotal)
                .setCorePoolSize(4)
                .build();

            this.instance.addEventListener(
                new ReadyListener(),
                new GuildListener()
            );

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

                // Fun
                new BunnyCommand(),
                new CatCommand(),
                new ChooseCommand(),
                new CookieCommand(),
                new DogCommand(),
                new EightballCommand(),
                new EmojiCommand(),
                new EmojifyCommand(),
                new FacesCommand(),
                new FlipCommand(),
                new JokeCommand(),
                new LmgtfyCommand(),
                new NatoCommand(),
                new QuoteCommand(),
                new RandomCommand(),
                new ReverseCommand(),
                new RockpaperscissorsCommand(),
                new RollCommand(),
                new RomanCommand(),
                new ThisorthatCommand(),
                new WouldyouratherCommand(),
                new YomammaCommand(),
                new ZalgolizeCommand(),

                // Integration
                new StrawpollCommand(),
                new UrbandictionaryCommand(),
                new WeatherCommand(),
                new WikipediaCommand(),
                new XkcdCommand(),

                // Interaction
                new HugCommand(),
                new PunchCommand(),
                new ShootCommand(),
                new SlapCommand(),
                new StabCommand(),

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
                new TempoCommand(),
                new UnqueueCommand(),
                new VolumeCommand(),

                // System
                new EvalCommand(),

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

            this.executorService.scheduleAtFixedRate(() -> this.instance.getPresence().setGame(Game.playing(Config.getConfig("discord").getString("prefix") + "help | " + ShardManager.getGuildCount() + " Guilds")), 30L, 60L, TimeUnit.SECONDS);
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
        return this.instance.getPing();
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
