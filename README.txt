Liam Jones
Yunman Hwang
Michael Moorman
Thanushen Balaskandar
Luke Sell
Iulian-Mihai Sava
Bo Zhao

------------------------
How to run:
Right-click pom.xml -> Run as -> Maven Build...
In "Goals" input: "clean install"
In "Profile" input one of the following (depending on which role you want to be): "Customer", "Waiter", "Kitchen", or "Manager"
In the "JRE" tab, ensure that you're using a JDK and not a JRE as this gave us errors - we're using JDK 1.8.2_202
Then press the "Run" button to build the program

Upon the build being successful, go to the "target" folder inside the project folder (in a file explorer), and run the "OAXACA-1.0.0.jar" file

This will start the program in the specified view (Profile)