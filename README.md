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


## Setting up the SDKs

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

## Setting up the Project Dependencies

- Download GARITS and extract it from the zip folder
- Make sure OpenJFX 11 is installed using the installer provided with the download
- Make sure OpenJDK 11 is downloaded and extracted in the Documents folder
- To make sure the database works correctly, copy test.mv.db and test.trace.db files from the GARITS folder
to your home directory.

Home directory is in different location depending on the OS:
- Windows : C:\Users\Username/
- macOS : /Users/Username/
- Linux : /home/username/

## Resolving Dependency Errors

All the dependencies are located in the following folder : GARITS/src/TwentyThreeProductions/GARITSpayload

- Navigate to GARITS/jasperdepend folder
- Extract both dist.zip and src.zip to your Documents folder
- To make sure all the external libraries work correctly with GARITS follow the below steps (IntelliJ):
1. File -> Project Structure -> Libraries
2. Click on a library that is highlighted in red
3. Click on the ‘-‘ sign to remove that library
4. Click on the ‘+’ sign and select the library that you just removed
5. Repeat for any library that is highlighted in red

## Future Plans 

- Do some housekeeping and make it more stable 

- Switch over to cloud-based DB for cross-platform functionality 

- Allow multi-instance running on different platforms 

- Make it work with GARITSiOS
