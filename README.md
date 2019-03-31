## Example of a simple JavaFX Application using MVC and DAO pattern
* Simple app to show how to implement:
 * GUI using SceneBuilder
 * Controller/Model Injectable
 * DAO for Oracle database connection
 * Strict separation between GUI and Model/DAO
 
### The Model-View-Controller design pattern (MVC Design Pattern)
is one of the most common pattern for structuring software. It allows a far-reaching separation of data model and its graphical representation.
A clear, clear structuring, the associated ease of maintenance and the reusability of program parts are among the top objectives of a successful program design. The MVC pattern allows the architectural main parts of software to be kept separate. Three parts are to be distinguished:
### The model
It contains the work data of a program, user input, data read from databases, etc.
### The presentation
The (in a desktop program of the same graphical) representation of data, the user interface (GUI), etc. You will find in the source text neither data, nor parts of the business or program logic. For this purpose, it defines appropriate interfaces.
### The controller
The mediating layer, which is responsible for the interaction between the presentation layer and the data. It is realized by an observer pattern that observes the data of the model. updated with the presentation layer. The controller must therefore have access to both the data and the presentation layer.

## The DataAccessObject
is the primary object of this pattern. The DataAccessObject abstracts the underlying data access implementation for the BusinessObject to enable transparent access to the data source.
## The DataSource
This represents a data source implementation. A data source could be a database such as an RDBMS, OODBMS, XML repository, flat file system, and so forth. A data source can also be another system service or some kind of repository.

 ### Planned Package Structure
```
my.package
    Application
my.package.model
    Model
my.package.services
    DAOService
my.package.ui
    MainController
    Main.fxml    
my.package.utils
    ControllerFactory
    ViewLoader
    ControllerInjectable(Interface)
    ModelInjectable (Interface)
    DAOServiceInjectable (Interface)
```
