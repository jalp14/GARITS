# GARITS


## Requirements

- IntelliJ Ultimate

- OpenJDK

- OpenJFX

- Some Patience

## Notes 

- Works fully on macOS and Linux 

- Works partially on Windows (Backup/Restore doesnt work -_- ) 

- Reports only work partially 



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

``--module-path
${PATH_TO_FX}
--add-modules=javafx.controls,javafx.fxml,javafx.web
--add-opens
javafx.base/com.sun.javafx.runtime=ALL-UNNAMED
--add-opens
javafx.controls/com.sun.javafx.scene.control.behavior=ALL-UNNAMED
--add-opens
javafx.controls/com.sun.javafx.scene.control=ALL-UNNAMED
--add-opens
javafx.base/com.sun.javafx.binding=ALL-UNNAMED
--add-opens
javafx.base/com.sun.javafx.event=ALL-UNNAMED
--add-opens
javafx.graphics/com.sun.javafx.stage=ALL-UNNAMED
--add-exports=javafx.graphics/com.sun.javafx.util=ALL-UNNAMED
--add-exports=javafx.base/com.sun.javafx.reflect=ALL-UNNAMED
--add-exports=javafx.base/com.sun.javafx.beans=ALL-UNNAMED
--add-exports=javafx.graphics/com.sun.glass.utils=ALL-UNNAMED``

Click Apply


## Future Plans 

- Do some housekeeping and make it more stable 

- Switch over to cloud-based DB for cross-platform functionality 

- Allow multiple instances of different platforms 

- Make it work with GARITSiOS
