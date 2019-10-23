# CPSC 501 Relfective Object Inspector

This program inspects Java objects during run time using reflection and gets the following information:
- The name of the declaring class
- The name of the immediate super-class
- The name of each interface the class implements
- The constructors the class declares. For each, also find the following:
	- The name, The parameter types, The modifiers
- The methods the class declares. For each, also find the following:
	- The name, The exceptions thrown, The return type, The modifiers
- The fields the class declares. For each, also find the following: 
	- The name, The type, The modifiers, The current value of each field


## Eclipse support

After importing the project to Eclipse, ensure that Gradle is configured. To configure it, do the following

- Right click on the project in the eclipse project explorer
- Select **Configure**
- Select **Add Gradle Nature**

## Running Project

- Right click on Project
- Run as **Java Application**
- Click Start - driver

## Testing Project

- Right click on Project
- Run as **JUnit Test**

**Note:** If project is not running, try restarting eclipse as grade needs to be reloaded.

## Authors

- [Rakheem Dewji](https://github.com/raksdewji)