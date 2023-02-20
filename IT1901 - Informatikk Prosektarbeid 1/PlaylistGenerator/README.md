[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2143/gr2143)

# Playlist Generator: group 2143 for it1901

This is a repository for group 2143 containing a project for the course it1901: Informatikk Prosjektarbeid 1. The project will be a Playlist Generator. There will be several releases of the project thoughout the semester. 

The project code can be found the package playlistGenerator, and withing the package there is also a more detailed description of the project and application. Within this package we have two packages more, the core where logic and filesaving will be handled, and also the ui-package where the interface and javafx code is located.  
In the docs package one can find documentation for every release.  

## Before running

- Navigate to root folder **playlistGenerator** and install with `mvn install`
- To start the server move to `cd restserver` and run the command: `mvn spring-boot:run`
- Open new terminal and navigate to root folder **playlistGenerator**
- To start the app move to `cd ui` and `mvn javafx:run`.

During this you will download a "server-file" which will end up in your users folder on your disk. This will be used during the app to communicate updates to the server file.

## Testing

- The application has tests to ensure that the code works, as well judging the code quality. To manage this, we have used junit5 to write our own tests. To check test coverage, jacoco has been used. For code quality, we have used checkstyles and spotbugs. 

- To run just the tests, navigate to the root folder and run the command 'mvn test'
- To run jacoco, checkstyles and spotbugs, navigate to the root folder and run the command 'mvn verify'. This will also run the same tests as with 'mvn test'.
- To be able to package the app and run it as a program on your computer, navigate to the ui module from the root folder. Then run this maven command: mvn clean compile javafx:jlink jpackage:jpackage. A folder named gr2143 will appear in the target folder in the ui module. Open this and you will find a file that will let you install the app on your computer.
