CAD
===============

Improvements to be made:
* Accept negative length values (but not negative coordinates)
* If a typo or mistake is made while writing input, prevent previous--intended--input from being erased (don't send the user back to the beginning of the method)
* Maybe even convert all input to a GUI
* Add 'bottom,' 'back,' and 'left' orientations (inverses of 'top,' 'front,' and 'side')
* Write selective erase methods (using the shape-building methods to insert 'void' characters instead of 'filled' characters) rather than just one 'clearAllLayers' method
* Allow user to choose the angles of a triangle
* Allow user to choose the height (and thus the taper angle) of the pyramidal shapes
* Write methods to draw shape *outlines* (or frames, for 3D shapes), not just solid objects
* Choose and implement keyword which will allow the user to exit the program at any point (i.e. 'quit' or 'exit')