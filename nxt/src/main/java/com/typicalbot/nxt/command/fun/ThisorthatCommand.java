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
package com.typicalbot.nxt.command.fun;

import com.typicalbot.command.*;

import java.util.Random;

@CommandConfiguration(category = CommandCategory.FUN, aliases = "thisorthat")
public class ThisorthatCommand implements Command {
    // TODO(nsylke): Move these to a file (in resources) or make an API.
    private String[] responses = new String[]{
        "Dog or Cat?",
        "Netflix or YouTube?",
        "Phone Call or Text?",
        "Toast or Eggs?",
        "Cardio or Weights?",
        "Facebook or Twitter?",
        "Ice Cream Cone or Snow Cone?",
        "Mobile Games or Console Games?",
        "While walking: Music or Podcasts?",
        "iOS or Android?",
        "Form or Function?",
        "Pop or Indie?",
        "Cake or Pie?",
        "Swimming or Sunbathing?",
        "High-tech or Low-tech?",
        "Big Party or Small Gathering?",
        "New Clothes or New Phone?",
        "Rich Friend or Loyal Friend?",
        "Football or Basketball?",
        "Work Hard or Play Hard?",
        "Nice Car or Nice Home Interior?",
        "What’s worse: Laundry or Dishes?",
        "Jogging or Hiking?",
        "Bath or Shower?",
        "Sneakers or Sandals?",
        "Glasses or Contacts?",
        "Hamburger or Taco?",
        "Couch or Recliner?",
        "Online Shopping or Shopping in a Store?",
        "Receive: Email or Letter?",
        "Passenger or Driver?",
        "Tablet or Computer?",
        "Most important in a partner: Intelligent or Funny?",
        "Car or Truck?",
        "Blue or Red?",
        "Money or Free Time?",
        "Amusement Park or Day at the Beach?",
        "At a movie: Candy or Popcorn?",
        "Pen or Pencil?",
        "Toilet paper: Over or Under?",
        "Cups in the cupboard: Right Side Up or Up Side Down?",
        "Pancake or Waffle?",
        "Coke or Pepsi?",
        "Coffee Cup or Thermos?",
        "Blinds or Curtain?",
        "Train or Plane?",
        "Phone or Phablet?",
        "Iced Coffee or Hot Coffee?",
        "Meat or Vegetables?",
        "International Vacation or a New TV?",
        "Save or Spend?",
        "Honesty or Other’s Feelings?",
        "Coffee or Tea?",
        "TV or Book?",
        "Movie at Home or Movie at the Theater?",
        "Ocean or Mountains?",
        "Horror Movie or Comedy Movie?",
        "City or Countryside?",
        "Winter or Summer?",
        "Mac or PC?",
        "Console Gaming or PC Gaming?",
        "Soup or Sandwich?",
        "Card Game or Board Game?",
        "Classical Art or Modern Art?",
        "Beer or Wine?",
        "Camping or Binge Watching Shows at Home?",
        "Working Alone or Working in a Team?",
        "Dine In or Delivery?",
        "Sweater or Hoodie?",
        "Comic Book or Comic Strips?",
        "Motorcycle or Bicycle?",
        "Book or eBook?",
        "When sleeping: Fan or No Fan?",
        "Ninjas or Pirates?",
        "TV Shows or Movies?"
    };

    @Override
    public String[] usage() {
        return new String[]{
            "thisorthat"
        };
    }

    @Override
    public String description() {
        return "Returns a this-or-that.";
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
