# GARITS



**Planned Features** 


**Requirements**

IntelliJ Ultimate

OpenJDK

OpenJFX

Some Patience


**Downloads**

OpenJDK : https://openjdk.java.net/

OpenJFX : https://openjfx.io/


**Setup**

Open Intellij and clone the project 

File -> Preferences -> Appereance & Behaviour -> Path Variable : Add New 

Name : PATH_TO_FX    
Value : Copy the path to javafx-sdk-11.0.2/lib

Save it!

Now build the project 

Run -> Edit Configurations

Add this to your VM options

--module-path={PATH_TO_FX} --add-modules=javafx.controls,javafx.fxml

Click Apply


 **Backup/Restore Command**
 Navigate to the assests folder and open a cmd/termial window and run :
 
 java -cp h2*.jar org.h2.tools.Backup -file "~/backup.zip" -dir "~"
