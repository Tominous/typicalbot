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
package com.typicalbot.command.fun;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;

import java.util.Random;

@CommandConfiguration(category = CommandCategory.FUN, aliases = "wouldyourather")
public class WouldyouratherCommand implements Command {
    // TODO(nsylke): Move these to a file (in resources) or make an API.
    private String[] responses = new String[]{
        "Would you rather always be 10 minutes late or always be 20 minutes early?",
        "Would you rather lose all of your money and valuables or all of the pictures you have ever taken?",
        "Would you rather be able to see 10 minutes into your own future or 10 minutes into the future of anyone but yourself?",
        "Would you rather be famous when you are alive and forgotten when you die or unknown when you are alive but famous after you die?",
        "Would you rather go to jail for 4 years for something you didn’t do or get away with something horrible you did but always live in fear of being caught?",
        "Would you rather accidentally be responsible for the death of a child or accidentally be responsible for the deaths of three adults?",
        "Would you rather your shirts be always two sizes too big or one size too small?",
        "Would you rather live in the wilderness far from civilization or live on the streets of a city as a homeless person?",
        "Would you rather the general public think you are a horrible person but your family be very proud of you or your family think you are a horrible person but the general public be very proud of you?",
        "Would you rather live your entire life in a virtual reality where all your wishes are granted or in the real world?",
        "Would you rather be alone for the rest of your life or always be surrounded by annoying people?",
        "Would you rather never use social media sites / apps again or never watch another movie or TV show?",
        "Would you rather have an easy job working for someone else or work for yourself but work incredibly hard?",
        "Would you rather be the first person to explore a planet or be the inventor of a drug that cures a deadly disease?",
        "Would you rather have a horrible short term memory or a horrible long term memory?",
        "Would you rather be completely invisible for one day or be able to fly for one day?",
        "Would you rather be locked in a room that is constantly dark for a week or a room that is constantly bright for a week?",
        "Would you rather be poor but help people or become incredibly rich by hurting people?",
        "Would you rather live without the internet or live without AC and heating?",
        "Would you rather have a horrible job, but be able to retire comfortably in 10 years or have your dream job, but have to work until the day you die?",
        "Would you rather find your true love or a suitcase with five million dollars inside?",
        "Would you rather be able to teleport anywhere or be able to read minds?",
        "Would you rather die in 20 years with no regrets or die in 50 years with many regrets?",
        "Would you rather be feared by all or loved by all?",
        "Would you rather know when you are going to die or how you are going to die? (You can’t change the time or method of your death.)",
        "Would you rather be transported permanently 500 years into the future or 500 years into the past?",
        "Would you rather never be able to use a touchscreen or never be able to use a keyboard and mouse?",
        "Would you rather be able to control fire or water?",
        "Would you rather have everything you eat be too salty or not salty enough no matter how much salt you add?",
        "Would you rather have hands that kept growing as you got older or feet that kept growing as you got older?",
        "Would you rather have unlimited sushi for life or unlimited tacos for life? (both are amazingly delicious and can be any type of sushi / taco you want)",
        "Would you rather be unable to use search engines or unable to use social media?",
        "Would you rather give up bathing for a month or give up the internet for a month?",
        "Would you rather donate your body to science or donate your organs to people who need them?",
        "Would you rather go back to age 5 with everything you know now or know now everything your future self will learn?",
        "Would you rather relive the same day for 365 days or lose a year of your life?",
        "Would you rather have a golden voice or a silver tongue?",
        "Would you rather be able to control animals (but not humans) with your mind or control electronics with your mind?",
        "Would you rather suddenly be elected a senator or suddenly become a CEO of a major company. (You won’t have any more knowledge about how to do either job than you do right now.)",
        "Would you rather sell all of your possessions or sell one of your organs?",
        "Would you rather lose all of your memories from birth to now or lose your ability to make new long term memories?",
        "Would you rather be infamous in history books or be forgotten after your death?",
        "Would you rather never have to work again or never have to sleep again (you won’t feel tired or suffer negative health effects)?",
        "Would you rather be beautiful / handsome but stupid or intelligent but ugly?",
        "Would you rather get one free round trip international plane ticket every year or be able to fly domestic anytime for free?",
        "Would you rather be balding but fit or overweight with a full head of hair?",
        "Would you rather be able to be free from junk mail or free from email spam for the rest of your life?",
        "Would you rather be fluent in all languages and never be able to travel or be able to travel anywhere for a year but never be able to learn a word of a different language?",
        "Would you rather have an unlimited international first class ticket or never have to pay for food at restaurants?",
        "Would you rather see what was behind every closed door or be able to guess the combination of every safe on the first try?",
        "Would you rather live in virtual reality where you are all powerful or live in the real world and be able to go anywhere but not be able to interact with anyone or anything?",
        "Would you rather never be able to eat meat or never be able to eat vegetables?",
        "Would you rather give up watching TV / movies for a year or give up playing games for a year?",
        "Would you rather always be able to see 5 minutes into the future or always be able to see 100 years into the future?",
        "Would you rather super sensitive taste or super sensitive hearing?",
        "Would you rather be a practicing doctor or a medical researcher?",
        "Would you rather be married to a 10 with a bad personality or a 6 with an amazing personality?",
        "Would you rather never be able to drink sodas like coke again or only be able to drink sodas and nothing else?",
        "Would you rather have amazingly fast typing / texting speed or be able to read ridiculously fast?",
        "Would you rather know the history of every object you touched or be able to talk to animals?",
        "Would you rather be a reverse centaur or a reverse mermaid/merman?",
        "Would you rather have constantly dry eyes or a constant runny nose?",
        "Would you rather be a famous director or a famous actor?",
        "Would you rather not be able to open any closed doors (locked or unlocked) or not be able to close any open doors?",
        "Would you rather give up all drinks except for water or give up eating anything that was cooked in an oven?",
        "Would you rather be constantly tired no matter how much you sleep or constantly hungry no matter what you eat? Assuming that there are no health problems besides the feeling of hunger and sleepiness.",
        "Would you rather have to read aloud every word you read or sing everything you say out loud?",
        "Would you rather have whatever you are thinking appear above your head for everyone to see or have absolutely everything you do live streamed for anyone to see?",
        "Would you rather be put in a maximum security federal prison with the hardest of the hardened criminals for one year or be put in a relatively relaxed prison where wall street types are held for ten years?",
        "Would you rather have a clown only you can see that follows you everywhere and just stands silently in a corner watching you without doing or saying anything or have a real life stalker who dresses like the Easter bunny that everyone can see?",
        "Would you rather kill one innocent person or five people who committed minor crimes?",
        "Would you rather have a completely automated home or a self-driving car?",
        "Would you rather work very hard at a rewarding job or hardly have to work at a job that isn’t rewarding?",
        "Would you rather be held in high regard by your parents or your friends?",
        "Would you rather be an amazing painter or a brilliant mathematician?",
        "Would you rather be reincarnated as a fly or just cease to exist after you die?",
        "Would you rather be able to go to any theme park in the world for free for the rest of your life or eat for free at any drive through restaurant for the rest of your life?",
        "Would you rather be only able to watch the few movies with a rotten tomatoes score of 95-100% or only be able to watch the majority of movies with a rotten tomatoes score of 94% and lower?",
        "Would you rather never lose your phone again or never lose your keys again?",
        "Would you rather have one real get out of jail free card or a key that opens any door?",
        "Would you rather have a criminal justice system that actually works and is fair or an administrative government that is free of corruption?",
        "Would you rather have real political power but be relatively poor or be ridiculously rich and have no political power?",
        "Would you rather have the power to gently nudge anyone’s decisions or have complete puppet master control of five people?",
        "Would you rather have everyone laugh at your jokes but not find anyone else’s jokes funny or have no one laugh at your jokes but you still find other people’s jokes funny?",
        "Would you rather be the absolute best at something that no one takes seriously or be well above average but not anywhere near the best at something well respected?",
        "Would you rather lose the ability to read or lose the ability to speak?",
        "Would you rather live under a sky with no stars at night or live under a sky with no clouds during the day?",
        "Would you rather humans go to the moon again or go to mars?",
        "Would you rather never get angry or never be envious?",
        "Would you rather have free Wi-Fi wherever you go or be able to drink unlimited free coffee at any coffee shop?",
        "Would you rather be compelled to high five everyone you meet or be compelled to give wedgies to anyone in a green shirt?",
        "Would you rather live in a house with see-through walls in a city or in the same see-though house but in the middle of a forest far from civilization?",
        "Would you rather take amazing selfies but all of your other pictures are horrible or take breathtaking photographs of anything but yourself?",
        "Would you rather use a push lawn mower with a bar that is far too high or far too low?",
        "Would you rather be able to dodge anything no matter how fast it’s moving or be able ask any three questions and have them answered accurately?",
        "Would you rather live on the beach or in a cabin in the woods?",
        "Would you rather lose your left hand or right foot?",
        "Would you rather face your fears or forget that you have them?",
        "Would you rather be forced to dance every time you heard music or be forced to sing along to any song you heard?",
        "Would you rather have skin that changes color based on your emotions or tattoos appear all over your body depicting what you did yesterday?",
        "Would you rather live in a utopia as a normal person or in a dystopia but you are the supreme ruler?",
        "Would you rather snitch on your best friend for a crime they committed or go to jail for the crime they committed?",
        "Would you rather have everything on your phone right now (browsing history, photos, etc.) made public to anyone who Google’s you name or never use a cell phone again?",
        "Would you rather eat a box of dry spaghetti noodles or a cup of uncooked rice?",
        "Would you rather wake up as a new random person every year and have full control of them for the whole year or once a week spend a day inside a stranger without having any control of them?",
        "Would you rather be born again in a totally different life or born again with all the knowledge you have now?",
        "Would you rather be forced to kill a kitten or kill a puppy?",
        "Would you rather be lost in a bad part of town or lost in the forest?",
        "Would you rather never get a paper cut again or never get something stuck in your eye again?",
        "Would you rather randomly time travel +/- 20 years every time you fart or teleport to a different place on earth (on land, not water) every time you sneeze?",
        "Would you rather the aliens that make first contact be robotic or organic?",
        "Would you rather be famous but ridiculed or be just a normal person?",
        "Would you rather be an amazing virtuoso at any instrument but only if you play naked or be able to speak any language but only if close your eyes and dance while you are doing it?",
        "Would you rather have a flying carpet or a car that can drive underwater?",
        "Would you rather be an amazing artist but not be able to see any of the art you created or be an amazing musician but not be able to hear any of the music you create?",
        "Would you rather find five dollars on the ground or find all of your missing socks?",
        "Would you rather there be a perpetual water balloon war going on in your city / town or a perpetual food fight?",
        "Would you rather never have another embarrassing fall in public or never feel the need to pass gas in public again?",
        "Would you rather be able to talk to land animals, animals that fly, or animals that live under the water?",
        "Would you rather lose your best friend or all of your friends except for your best friend?",
        "Would you rather it be impossible for you to be woken up for 11 straight hours every day but you wake up feeling amazing or you can be woken up normally but never feel totally rested?",
        "Would you rather wake up every morning with a new hundred-dollar bill in your pocket but not know where it came from or wake up every morning with a new fifty-dollar bill in your pocket and know where it comes from?",
        "Would you rather everything you dream each night come true when you wake up or everything a randomly chosen person dreams each night come true when they wake up?",
        "Would you rather get 5 dollars for every song you sang in public or 50 dollars for every stranger you kiss?",
        "Would you rather have a boomerang that would find and kill any one person of your choosing, anywhere in the world, but can only be used once or a boomerang that always returns to you with one dollar?",
        "Would you rather have every cat or dog that gets lost end up at your house or everyone’s clothes that they forget in the dryer get teleported to your house?",
        "Would you rather never be stuck in traffic again or never get another cold?",
        "Would you rather have to fart loudly once, every time you have a serious conversation or have to burp after every kiss?",
        "Would you rather know how above or below average you are at everything or know how above or below average people are at one skill / talent just by looking at them?",
        "Would you rather know the uncomfortable truth of the world or believe a comforting lie?",
        "Would you rather blink at twice the normal rate or not be able to blink for 5 minutes but then have to close your eyes for 10 seconds every 5 minutes?",
        "Would you rather have a cute well behaved child that stays at an age of your choosing for their entire life or a child that develops from a baby to 18 years old in 2 years and then ages normally?",
        "Would you rather it never stops snowing (the snow never piles up) or never stops raining (the rain never causes floods)?",
        "Would you rather have a bottomless box of Legos or a bottomless gas tank?",
        "Would you rather all conspiracy theories be true or live in a world where no leaders really know what they are doing?",
        "Would you rather all plants scream when you cut them / pick their fruit or animals beg for their lives before they are killed?",
        "Would you rather wake up each morning to find that a random animal appendage has replaced your non dominant arm or permanently replace your bottom half with an animal bottom of your choice?",
        "Would you rather every shirt you ever wear to be kind of itchy or only be able to use 1 ply toilet paper?",
        "Would you rather go bald or be forever cursed to have terrible haircuts?",
        "Would you rather not be able to see any colors or have mild but constant tinnitus (ringing in the ears)?",
        "Would you rather never be able to leave your own country or never be able to fly in an airplane?",
        "Would you rather fight for a cause you believe in but doubt will succeed or fight for a cause that you only partially believe in but have a high chance of your cause succeeding?",
        "Would you rather have no fingers or no elbows?",
        "Would you rather have edible spaghetti hair that regrows every night or sweat maple syrup?",
        "Would you rather have everything in your house perfectly organized by a professional or have a professional event company throw the best party you’ve ever been to, in your honor?",
        "Would you rather have one nipple or two belly buttons?",
        "Would you rather be forced to eat only spicy food or only incredibly bland food?",
        "Would you rather never have a toilet clog on you again or never have the power go out again?",
        "Would you rather have all traffic lights you approach be green or never have to stand in line again?",
        "Would you rather have all of your clothes fit perfectly or have the most comfortable pillow, blankets, and sheets in existence?",
        "Would you rather wake up in the middle of an unknown desert or wake up in a row boat on an unknown body of water?",
        "Would you rather be famous for inventing a deadly new weapon or invent something that helps the world but someone else gets all the credit for inventing it?",
        "Would you rather 5% of the population have telepathy or 5% of the population have telekinesis? You are not part of the 5% that has telepathy or telekinesis.",
        "Would you rather earbuds and headphones never sit right on / in your ears or have all music either slightly too quiet or slightly too loud?",
        "Would you rather always have a great body for your entire life but have slightly below average intelligence or have a mediocre body for your entire life but be slightly above average in intelligence?",
        "Would you rather become twice as strong when both of your fingers are stuck in your ears or crawl twice as fast as you can run?",
        "Would you rather be in debt for $100,000 or never be able to make more than $3,500 a month?",
        "Would you rather be the best in the world at climbing trees or the best in the world at jumping rope?",
        "Would you rather have everything you draw become real but be terrible at drawing or be able to fly but only as fast as you can walk?",
        "Would you rather have a map that shows you the location of anything you want to find and can be used again and again but has a margin of error of up to a mile or a device that allows you to find the location of anything you want with incredible accuracy but can only be used three times?",
        "Would you rather never run out of battery power for whatever phone and tablet you own or always have free Wi-Fi wherever you go?",
        "Would you rather live until you are 200 but look like you are 200 the whole time even though you are healthy or look like you are 25 all the way until you die at age 65?",
        "Would you rather live a comfortable and peaceful life in the woods in a small cabin or a life full of conflict in a mansion in a city?",
        "Would you rather 20 butterflies instantly appear from nowhere every time you cough or 100 butterflies die somewhere in the world every time you cough?",
        "Would you rather have unlimited amounts of any material you want to build a house but you have to build the house yourself or have a famed architect design and build you a modest house?",
        "Would you rather have all animals feel compelled to obey you if you come within 10 feet of them or be given the opportunity to genetically design a pet that will be loyal only to you with the combined DNA of three animals?",
        "Would you rather be covered in fur or covered in scales?",
        "Would you rather your only mode of transportation be a donkey or a giraffe?",
        "Would you rather live in a cave or live in a tree house?",
        "Would you rather never sweat again or never feel cold again?",
        "Would you rather never have to clean a bathroom again or never have to do dishes again?",
        "Would you rather eat an egg with a half formed chicken inside or eat five cooked cockroaches?",
        "Would you rather wear a wedding dress / tuxedo every single day or wear a bathing suit every single day?",
        "Would you rather be an unimportant character in the last movie you saw or an unimportant character in the last book you read?",
        "Would you rather move to a new city or town every week or never be able to leave the city or town you were born in?",
        "Would you rather be a bowling champion or a curling champion?",
        "Would you rather have the police hunting you for a murder you didn’t commit or a psychopathic clown hunting you?",
        "Would you rather be so afraid of heights that you can’t go to the second floor of a building or be so afraid of the sun that you can only leave the house on rainy days?",
        "Would you rather live in a haunted house where the ghosts ignore you and do their own thing or be a ghost in a house living out a pleasant and uneventful week from your life again and again?",
        "Would you rather be an average person in the present or a king 2500 years ago?",
        "Would you rather be fantastic at riding horses or amazing at driving dirt bikes?",
        "Would you rather write a novel that will be widely considered the most important book in the past 200 years but you and the book will only be appreciated after your death or be the most famous erotica writer of your generation?",
        "Would you rather spend the rest of your life with a sailboat as your home or an RV as your home?",
        "Would you rather get really tipsy from just one sip of alcohol and ridiculously drunk from just one alcoholic drink or never get drunk no matter how much alcohol you drank?",
        "Would you rather be completely insane and know that you are insane or completely insane and believe you are sane?",
        "Would you rather vomit uncontrollably for one-minute every time you hear the happy birthday song or get a headache that lasts for the rest of the day every time you see a bird (including in pictures or a video)?",
        "Would you rather be hired for a well-paying job that you lied to get and have no idea how to do or be about to give the most important presentation of your life but you can’t remember any of the material you prepared?",
        "Would you rather walk around work or school for the whole day without realizing there is a giant brown stain on the back of your pants or realize the deadline for that important paper / project was yesterday and you are nowhere near done?",
        "Would you rather eat a sandwich made from 4 ingredients in your fridge chosen at random or eat a sandwich made by a group of your friends from 4 ingredients in your fridge?",
        "Would you rather have someone impersonating you and doing really amazing things that you get the credit for or find money hidden in weird places all around your house every day but with no explanation where the money comes from or how it keeps getting there?",
        "Would you rather have plants grow at 10 times their normal rate when you are near or for people and animals to stop aging when you are near them?",
        "Would you rather have chapped lips that never heal or a terrible dandruff that can’t be treated?",
        "Would you rather have out of control body hair or out of control body odor?",
        "Would you rather know all the mysteries of the universe or know every outcome of every choice you make?",
        "Would you rather have someone secretly give you LSD on a random day and time once every 6 months or have everyone in the world all take LSD at the same time once every 5 years?",
        "Would you rather be the life of the party and the funniest person your friends know but suffer from depression or be happy and content but people think you are boring and unfunny?",
        "Would you rather never be able to wear pants or never be able to wear shorts?",
        "Would you rather be unable to have kids or only be able to conceive quintuplets?",
        "Would you rather only wear one color each day or have to wear seven colors each day?",
        "Would you rather eat rice with every meal or eat bread with every meal?",
        "Would you rather have no eyebrows or only one eyebrow?",
        "Would you rather all electrical devices mysteriously stop working (possibly forever) or the governments of the world are only run by people going through puberty?",
        "Would you rather everyone be required to wear identical silver jump suits or any time two people meet and are wearing an identical article of clothing they must fight to the death?",
        "Would you rather have all dogs try to attack you when they see you or all birds try to attack you when they see you?",
        "Would you rather be unable to move your body every time it rains or not be able to stop moving while the sun is out?",
        "Would you rather die if you didn’t slap a new person on the butt every 12 hours or die if you didn’t kill someone every year?",
        "Would you rather spend two years with your soul mate only to have them die and you never love again or spend your life with someone nice you settled for?"
    };

    @Override
    public String[] usage() {
        return new String[]{
            "wouldyourather"
        };
    }

    @Override
    public String description() {
        return "Returns a would-you-rather.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        context.sendMessage(responses[new Random().nextInt(responses.length)]);
    }
}
