# University Study Room Booking App Prototype
    What is UBCORB?:
UBCORB originally started as a 2020 Hackathon project; The purpose of this application is to provide an efficient and simple way for students to book study spaces on University of British Columbia - Okanagan (UBCO). The current method for booking study spaces requires users to utilize UBC's study booking website; Which isn't optimized for mobile browsers and/or smaller devices. Personally, as a student myself I understand the annoyance that comes with booking study spaces on campus; This application is designed to serve the students of UBCO. 

**Documentation for application (Google Drive Link):**
https://drive.google.com/drive/folders/1KlZVa4FYj4zvlR_YZidqJjvvS5IzXTv5?usp=sharing


*Please note, this project is also a submission for a COSC 341 - Human Computer Interaction Assignment.*

 
    Project Overview:
	
![Sample Screenshots](preview.jpg)
 
    How to run the application? 
**Please follow one of the two methods provided below:**
1) Download the (.zip) folder, or clone the repository into Android Studio.
2) [Access the APK folder](Apk)

        
**What I learned from this project:**

From this project I've learned a lot about the back-end work that goes into creating a mobile application. The front-end design is rather fun work, and relatively easier to implement; However the back-end is the core backbone of the application. I presented this application idea to UBC and they loved the concept. They understood the need for the application, and said they would consider implementing a mobile solution for students to book study spaces on their campuses. UBCO didn't grant us access to their NOVELL API, due to the fact that it would create extra work for their developers to maintain. They stressed on the maintainability for the application, for when i graduate and move on from my studies there. They also said that they would be removing their NOVELL API entirelly anyways, as they are looking to make the switch to a more central used API across their campuses (such as CWL). Another important thing i learned from this project was the implementation of the buildings in the code. They were hardcoded to present the current buildings on campus, however the issue arises if UBC decides to create or demolish a building on campus, then the hardcoded options within our application would have to be manually updated by a developer. Whilst there are many solutions to fixing this problem, i Hope this git repo encourages other students to push for a better mobile solution to booking study spaces for students on campus!

	  Footnote:
		
This application was implemented using a Pixel API 28, but is also optimized for other devices. Please ensure you have a fairly updated API and version of Android Studio.
	
This project currently is in testing phases. This application is currently being tested against a Firebase DB, until an implementation is made later on to allow the application to interact with UBC's website.




