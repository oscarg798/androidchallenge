# Knomatic Android Code Challenge
This repo contains the Knomatic Android code challenge.  

## Task
Your task is to modify this application so that it tells the current and forecast weather at the user's location.  You will need to get the user's GPS location and then use that to contact the [Dark Sky Forecast API](https://developer.forecast.io/) and display the weather and forecast. After that is completed, please complete the questionnaire at the bottom of this readme file.  Please include your answers inline in this file.

Your time is valuable and we understand that.  Our goal is for you to spend about three hours working on this task.  If you are having fun and want to take it farther feel free, but it's not required. Our evaluation criteria are listed below.  Please make sure that all of the evaluation sections are included.  And to be clear we don't expect them to be 100% production ready, just enough to show that you know what you're doing in each area.

**_A note about the Dark Sky Forecast API Key_**:
[The Dark Sky Forecast API](https://developer.forecast.io/) has free tier that allows about 1000 calls a day, you can sign up for an API key today without giving them any payment details.  If this changes or you have a problem, please email us and we will get you an API key.

## Requirements
- You must finish this application so that it can contact the [Dark Sky Forcast API](https://developer.forecast.io/) for the users location, and display the current weather and the forcast.
- Your submission must be a fork of this repo and a submitted as a pull request on GitHub
- You should add any needed screens/UI elements
- You should support both Tablets and Phones, but doesn't have to perfect
- You must answer the questions inline in this file.

## Notes and Hints
- Use Android Studio's code generation where you can.
- You may use any JSON library you want
- You may use any other open source libraries you want
- Its nice when things look nice, but we know you're not a designer.  A set of weather icons can be found here: http://adamwhitcroft.com/climacons/ and a design mock of our checkin weather screen is included in this repo at /mock.png .  Your app dosn't need to look like the mock, its just for inspiration to help get you started.   

## Evaluation
-   Correct Use of Fragments
-   Correct Use of Activities
-   Correct Use of Layouts
-   Correct Use of Styles and Other Resource Files
-   Correct Use of JSON API's
-   Correct Use of Location Services
-   Correct use of Application and fragment contexts.
-   Use of Android Compatibility Framework.
-   How is state passed between activities
-   How is the JSON deserialized
-   Markdown formatting
-   Defensive programming   (App shouldn't crash if GPS or network is off)

## Bonus Points
-  Some working unit tests!  

## Questions to Answer
Please answer the following in about a paragraph.

1.  How long did you spend on completing this challenge?
	I spend 14 hours

2.  How far were you at 3 hours?
	In three hours I had the forecast information consumed it, and the user location. But at that point I did not have any UI created, because I wanted  data layer ready as the first task

3.  What needs to be finished/fixed for this to be use in a production app?
	So many things, first I will have to translate all the strings for support multiple languages. I'm using many pop ups to avoid the app crash (Show message to user, to let him decide what to do) and I think I can improve this and show less dialogues. when the app go to the foreground I'm saving current the state, but only if the user was watching the forecast, so I thing it will need an improvement to save the user location too and avoid request it multiples times. 

	The app will need to show to the user the forecast per hour, because I have the data but I did not show it.

	the app will need a service to request forecast updates based on last known location, so the user get the right information every time, and  will be nice send this notifications to a wear device using appcompat notification builder. 

	Improve UX adding sound and more cool animations.

	Let the user specify multiples locations in which he wants to receive updates.

	Save into any kind of persistent storage the last forecast update, and create an update method to let the user watch the forecast without waiting, and when we receive updates refresh the UI or show a notifications if the user has close the application

	Test..Test and still testing 

4.  What did you think of this challenge?
	It's a nice challenge, it evaluates many aspects 

5.  What part was unclear?
	The API documentation. I think is better analyze the json response. And the API documentation needs to add a glossary right now, because developers are not Environmental engineers

6.  What could we do to make this challenge better?
	Have another API, and compare the forecast and decide which one show to the user. 

	Let the user search a location by name in which he wants to watch the weather

7.  What did we forget to test you on?
	SQLite, google maps 