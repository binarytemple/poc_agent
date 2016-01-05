# poc_agent

A simple example of implementing a Java agent. This agent intercepts attempts to load class files and outputs package/class info to stdout

# TODO
 
Create an example of preventing classfile caching.

That is to say, implement a means whereby every time a class is instantiated,
the class definition is loaded from a (Guava) cache which I have defined myself.