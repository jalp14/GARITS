# GARITS



## Planned Features 

https://docs.google.com/document/d/1cHOFNcVIK1OWNApO_lTYs3NArSjsSaeqyVOjFu4Yy1I/edit


## Requirements

- IntelliJ Ultimate

- OpenJDK

- OpenJFX

- Some Patience



## Downloads

- [IntelliJ Ultimate](https://www.jetbrains.com/idea/download/#section=windows)

- [OpenJDK](https://openjdk.java.net/)

- [OpenJFX](https://openjfx.io/)



## Setup

Move the OpenJFX SDK in your Docuemnts Folder

Open Intellij and clone the project 

File -> Project Structure -> Libraries -> + 

- Select the lib folder in your JavaFX folder 

- Click Apply!

File -> Preferences -> Appereance & Behaviour -> Path Variable : Add New 

- Name :`` PATH_TO_FX ``   
- Value : Copy the path to javafx-sdk-11.0.2/lib

Save it!

Now build the project 

Run -> Edit Configurations

- Add this to your VM options

`` --module-path ${PATH_TO_FX} --add-modules=javafx.controls,javafx.fxml ``

Click Apply



 ## Backup/Restore Command (Ignore this)
 
 Navigate to the assests folder and open a cmd/termial window and run :
 
 `` java -cp h2*.jar org.h2.tools.Backup -file "~/backup.zip" -dir "~" ``
 
 ## How SceneSwitch works
 
 **Initialising view for the first time**
 
 - Get instance of SceneSwitch and call the func : `` activateScene(String name, Scene scene) ``
 
 - This will present the view on your main screen
 
 - Inside the `` initialize() `` instantiate SceneSwitch and call the func : `` addScene(Parent parent, String name) ``
 
 - This will add the current parent(view) to SceneMap
 
 **Switching between scenes**
 
 - To switch to a different scene call the func : `` switchScene(String name) ``
 
 - This will change the parent(view) to the specified one given that you have initialised it before 
