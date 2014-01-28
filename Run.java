import java.io.*;
import java.util.*;

public class Run {


    Scanner scanner = new Scanner(System.in);
    String answer = "";
    CAD c;


    public void greeting() {
	System.out.println();
	System.out.println("Welcome to the highly inflexible CAD system!");
	try{Thread.sleep(1000);}catch(InterruptedException e){}
	intro();
    }


    public void intro() {
	System.out.println();
	System.out.println("Would you like to create a custom grid?  (Type \"yes\" or \"no\")");
	System.out.println();
	answer = scanner.next();
	int x=10,y=10,z=10;
	char v='.',f='O';
	if (answer.equals("yes")){
	    System.out.println("Do you want to choose the grid's dimensions, the characters which will fill it, or both? (Type \"dimensions,\" \"characters,\" or \"both.\")");
	    System.out.println();
	    answer = scanner.next();
	    if (answer.equals("both")) {
		System.out.println("Enter the length:");
		x = scanner.nextInt();
		System.out.println("Enter the width:");
		y = scanner.nextInt();
		System.out.println("Enter the height:");
		z = scanner.nextInt();
		System.out.println("Enter the character which will represent an unfilled space (spaces, ' ', don't work):");
		v = scanner.next().charAt(0);
		System.out.println("Enter the character which will represent a filled space:");
		f = scanner.next().charAt(0);
	    } else if (answer.equals("dimensions")) {
		System.out.println("Enter the length:");
		x = scanner.nextInt();
		System.out.println("Enter the width:");
		y = scanner.nextInt();
		System.out.println("Enter the height:");
		z = scanner.nextInt();
	    } else if (answer.equals("characters")) {
		System.out.println("Enter the character which will represent an unfilled space (spaces, ' ', don't work):");
		v = scanner.next().charAt(0);
		System.out.println("Enter the character which will represent a filled space:");
		f = scanner.next().charAt(0);
	    } else {
		System.out.println("intro: Invalid input.  Would you like to try again?  (Type \"yes\" or \"no\")");
		answer = scanner.next();
		if (answer.equals("yes"))
		    intro();
	    }
	}
	c = new CAD(x,y,z,v,f);
	System.out.println("Your grid has been successfully created with length "+x+", width "+y+", and height "+z+".  The character which represents an unfilled space in the grid is '"+v+"' and the character which represents a filled space is '"+f+"'.");
	System.out.println();
        menu();
    }


    public void menu() {
	try{Thread.sleep(500);}catch(InterruptedException e){}
	System.out.println();
	System.out.println("What would you like to do now?");
	System.out.println("\n build \n erase \n view current grid \n clear current grid \n change character settings \n start over \n exit program");
	System.out.println();
	answer = scanner.nextLine();
	if (answer.equals(""))
	    answer = scanner.nextLine();
	if (answer.equals("build")) {
	    build();
	} else if (answer.equals("erase")) {
	    erase();
	} else 	if (answer.equals("view current grid")||answer.equals("view")) {
	    view();
	} else 	if (answer.equals("clear current grid")||answer.equals("clear")) {
	    c.clearAllLayers();
	    System.out.println("Grid has been cleared.");
	    menu();
	} else if (answer.equals("change character settings")||answer.equals("change")||answer.equals("characer")||answer.equals("settings")) {
	    changeCharacters();
	} else 	if (answer.equals("start over")) {
	    intro();
	} else  if(answer.equals("exit program")||answer.equals("exit")||answer.equals("quit")) {
	    System.out.println("Goodbye.");
	    System.exit(0);
	} else {
	    System.out.println("menu: Invalid input.  Try again.");
	    menu();
	}
    }


    public void build() {
	System.out.println();
	System.out.println("What would you like to draw?  Type one of the following:");
	System.out.println("\n point \n line \n rectangle \n rectangular prism \n rectangular pyramid \n triangle \n triangular prism \n triangular pyramid \n circle \n cylinder \n cone \n sphere");
	System.out.println();

	answer = scanner.nextLine();

	if (answer.equals("point")) {
	    int x,y,z;
	    System.out.println("Enter the x-coordinate:");
	    x = scanner.nextInt();
	    System.out.println("Enter the y-coordinate:");
	    y = scanner.nextInt();
	    System.out.println("Enter the z-coordinate:");
	    z = scanner.nextInt();
	    try {
		c.makePoint(x,y,z);
		success(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("makePoint: out of bounds");
		success(false);
	    }
	    
	} else if (answer.equals("line")) {
	    int s,p,depth,length;
	    String orientation;
	    System.out.println("Enter the orientation* of the line (top, front, or side): \n *it will be drawn vertically relative to the orientation you chose \n");
	    orientation = scanner.next();
	    System.out.println("Enter the depth at which the line should be inserted from the view you chose:");
	    depth = scanner.nextInt();
	    System.out.println("Enter the horizontal coordinate of the first point:");
	    s = scanner.nextInt();
	    System.out.println("Enter the vertical coordinate of the first point:");
	    p = scanner.nextInt();
	    System.out.println("Enter the length (change in x) of the line:");
	    length = scanner.nextInt();
	    try {
		c.makeLine(s,p,depth,length,orientation);
		success(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("makeLine: out of bounds");
		success(false);
	    }

	} else 	if (answer.equals("rectangle")) {
	    int s,p,depth,length,width;
	    String orientation;
	    System.out.println("Enter the orientation of the rectangle (top, front, or side):");
	    orientation = scanner.next();
	    System.out.println("Enter the depth at which the rectangle should be inserted from the view you chose:");
	    depth = scanner.nextInt();
	    System.out.println("Enter the horizontal coordinate of the first point:");
	    s = scanner.nextInt();
	    System.out.println("Enter the vertical coordinate of the first point:");
	    p = scanner.nextInt();
	    System.out.println("Enter the length (change in x):");
	    length = scanner.nextInt();
	    System.out.println("Enter the width (change in y):");
	    width = scanner.nextInt();
	    try {
		c.makeRectangle(s,p,depth,length,width,orientation);
		success(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("makeRectangle: out of bounds");
		success(false);
	    }

	} else 	if (answer.equals("rectangular prism")) {
	    int s,p,q,length,width,height;
	    System.out.println("Enter the x-coordinate of the first point:");
	    s = scanner.nextInt();
	    System.out.println("Enter the y-coordinate of the first point:");
	    p = scanner.nextInt();
	    System.out.println("Enter the z-coordinate of the first point:");
	    q = scanner.nextInt();
	    System.out.println("Enter the length (change in x):");
	    length = scanner.nextInt();
	    System.out.println("Enter the width (change in y):");
	    width = scanner.nextInt();
	    System.out.println("Enter the height (change in z):");
	    height = scanner.nextInt();
	    try {
		c.makeRectangularPrism(s,p,q,length,width,height);
		success(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("makeRectangularPrism: out of bounds");
		success(false);
	    }

	} else 	if (answer.equals("rectangular pyramid")) {
	    int s,p,baselayer,length,width;
	    String orientation;
	    System.out.println("Enter the orientation of the pyramid (top, front, or side):");
	    orientation = scanner.next();
	    System.out.println("Enter the depth at which the base of the pyramid will lie:");
	    baselayer = scanner.nextInt();
	    System.out.println("Enter the horizontal coordinate of the first point:");
	    s = scanner.nextInt();
	    System.out.println("Enter the vertical coordinate of the first point:");
	    p = scanner.nextInt();
	    System.out.println("Enter the length (change in x) of the base:");
	    length = scanner.nextInt();
	    System.out.println("Enter the width (change in y) of the base:");
	    width = scanner.nextInt();
	    System.out.println("The pyramid's height will be determined based on its base.");
	    try {
		c.makeRectangularPyramid(s,p,baselayer,length,width,orientation);
		success(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("makeRectangularPyramid: out of bounds");
		success(false);
	    }

	} else 	if (answer.equals("triangle")) {
	    int s,p,depth,height;
	    String orientation;
	    System.out.println("Enter the orientation of the triangle (top, front, or side):");
	    orientation = scanner.next();
	    System.out.println("Enter the depth at which the triangle should be inserted from the view you chose:");
	    depth = scanner.nextInt();
	    System.out.println("Enter the x-coordinate of the top point:");
	    s = scanner.nextInt();
	    System.out.println("Enter the y-coordinate of the top point:");
	    p = scanner.nextInt();
	    System.out.println("Enter the height of the triangle:");
	    height = scanner.nextInt();
	    System.out.println("The triangle's base length will be determined based on its height.");
	    try {
		c.makeTriangle(s,p,depth,height,orientation);
		success(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("makeTriangle: out of bounds");
		success(false);
	    }

	} else 	if (answer.equals("triangular prism")) {
	    int s,p,baselayer,triangleheight,prismheight;
	    String orientation;
	    System.out.println("Enter the orientation of the prism (top, front, or side):");
	    orientation = scanner.next();
	    System.out.println("Enter the depth at which the base of the prism will lie:");
	    baselayer = scanner.nextInt();
	    System.out.println("Enter the horizontal coordinate of the top point of the base:");
	    s = scanner.nextInt();
	    System.out.println("Enter the vertical coordinate of the top point of the base:");
	    p = scanner.nextInt();
	    System.out.println("Enter the height of the base:");
	    triangleheight = scanner.nextInt();
	    System.out.println("The pyramid's base length will be determined based on its height.");
	    System.out.println("Enter the height of the prism:");
	    prismheight = scanner.nextInt();
	    try {
		c.makeTriangularPrism(s,p,baselayer,triangleheight,prismheight,orientation);
		success(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("makeTriangularPrism: out of bounds");
		success(false);
	    }

	} else 	if (answer.equals("triangular pyramid")) {
	    int s,p,baselayer,triangleheight;
	    String orientation;
	    System.out.println("Enter the orientation of the pyramid (top, front, or side):");
	    orientation = scanner.next();
	    System.out.println("Enter the depth at which the base of the pyramid will lie:");
	    baselayer = scanner.nextInt();
	    System.out.println("Enter the horizontal coordinate of the top point of the base:");
	    s = scanner.nextInt();
	    System.out.println("Enter the vertical coordinate of the top point of the base:");
	    p = scanner.nextInt();
	    System.out.println("Enter the height of the triangle:");
	    triangleheight = scanner.nextInt();
	    System.out.println("The triangle's base length will be determined based on its height.");
	    System.out.println("The pyramid's height will be determined based on its base.");
	    try { 
		c.makeTriangularPyramid(s,p,baselayer,triangleheight,orientation);
		success(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("makeTriangularPyramid: out of bounds");
		success(false);
	    }

	} else 	if (answer.equals("circle")) {
	    int h,k,radius,depth;
	    String orientation;
	    System.out.println("Enter the orientation of the circle (top, front, or side):");
	    orientation = scanner.next();
	    System.out.println("Enter the depth at which the circle should be inserted from the view you chose:");
	    depth = scanner.nextInt();
	    System.out.println("Enter the horizontal coordinate of the center point:");
	    h = scanner.nextInt();
	    System.out.println("Enter the vertical coordinate of the center point:");
	    k = scanner.nextInt();
	    System.out.println("Enter the radius of the circle:");
	    radius = scanner.nextInt();
	    try {
		c.makeCircle(h,k,radius,depth,orientation);	    
		success(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("makeCircle: out of bounds");
		success(false);
	    }
		
	} else 	if (answer.equals("cylinder")) {
	    int h,k,baselayer,radius,height;
	    String orientation;
	    System.out.println("Enter the orientation of the cylinder (top, front, or side):");
	    orientation = scanner.next();
	    System.out.println("Enter the depth at which the base of the cylinder will lie:");
	    baselayer = scanner.nextInt();
	    System.out.println("Enter the horizontal coordinate of the center point of the base:");
	    h = scanner.nextInt();
	    System.out.println("Enter the vertical coordinate of the center point of the base:");
	    k = scanner.nextInt();
	    System.out.println("Enter the radius:");
	    radius = scanner.nextInt();
	    System.out.println("Enter the height of the cylinder:");
	    height = scanner.nextInt();
	    try {
		c.makeCylinder(h,k,baselayer,radius,height,orientation);
		success(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("makeCylinder: out of bounds");
		success(false);
	    }

	} else 	if (answer.equals("cone")) {
	    int h,k,radius,baselayer;
	    String orientation;
	    System.out.println("Enter the orientation of the cone (top, front, or side):");
	    orientation = scanner.next();
	    System.out.println("Enter the depth at which the base of the cone will lie:");
	    baselayer = scanner.nextInt();
	    System.out.println("Enter the horizontal coordinate of the center point of the base:");
	    h = scanner.nextInt();
	    System.out.println("Enter the vertical coordinate of the center point of the base:");
	    k = scanner.nextInt();
	    System.out.println("Enter the radius of the base:");
	    radius = scanner.nextInt();
	    System.out.println("The cone's height will be determined based on its base.");
	    try {
		c.makeCone(h,k,radius,baselayer,orientation);
		success(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("makeCone: out of bounds");
		success(false);
	    }

	} else 	if (answer.equals("sphere")) {
	    int s,p,q,radius;
	    System.out.println("Enter the x-coordinate of the center point of the sphere:");
	    s = scanner.nextInt();
	    System.out.println("Enter the y-coordinate of the center point of the sphere:");
	    p = scanner.nextInt();
	    System.out.println("Enter the z-coordinate of the center point of the sphere:");
	    q = scanner.nextInt();
	    System.out.println("Enter the radius:");
	    radius = scanner.nextInt();
	    try {
		c.makeSphere(s,p,q,radius);
		success(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("makeSphere: out of bounds");
		success(false);
	    }

	} else {
	    System.out.println("build: Invalid input.  Try again.");
	    build();

	}

    }


    public void success(boolean b) {

	if (b)
	    System.out.println("Your shape was successfully entered into the grid.");
	else
	    System.out.println("Your shape was not successfully entered into the grid.");
	menu();

    }


    public void erase() {
	System.out.println();
	System.out.println("With which shape would you like to erase?  Type one of the following:");
	System.out.println("\n point \n line \n rectangle \n rectangular prism \n rectangular pyramid \n triangle \n triangular prism \n triangular pyramid \n circle \n cylinder \n cone \n sphere");
	System.out.println();

	answer = scanner.nextLine();

	if (answer.equals("point")) {
	    int x,y,z;
	    System.out.println("Enter the x-coordinate:");
	    x = scanner.nextInt();
	    System.out.println("Enter the y-coordinate:");
	    y = scanner.nextInt();
	    System.out.println("Enter the z-coordinate:");
	    z = scanner.nextInt();
	    try {
		c.erasePoint(x,y,z);
		erasedIndeed(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("erasePoint: out of bounds");
		erasedIndeed(false);
	    }
	    
	} else if (answer.equals("line")) {
	    int s,p,depth,length;
	    String orientation;
	    System.out.println("Enter the orientation* of the line (top, front, or side): \n *it will be drawn vertically relative to the orientation you chose \n");
	    orientation = scanner.next();
	    System.out.println("Enter the depth at which the line should be inserted from the view you chose:");
	    depth = scanner.nextInt();
	    System.out.println("Enter the horizontal coordinate of the first point:");
	    s = scanner.nextInt();
	    System.out.println("Enter the vertical coordinate of the first point:");
	    p = scanner.nextInt();
	    System.out.println("Enter the length (change in x) of the line:");
	    length = scanner.nextInt();
	    try {
		c.eraseLine(s,p,depth,length,orientation);
		erasedIndeed(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("eraseLine: out of bounds");
		erasedIndeed(false);
	    }

	} else 	if (answer.equals("rectangle")) {
	    int s,p,depth,length,width;
	    String orientation;
	    System.out.println("Enter the orientation of the rectangle (top, front, or side):");
	    orientation = scanner.next();
	    System.out.println("Enter the depth at which the rectangle should be inserted from the view you chose:");
	    depth = scanner.nextInt();
	    System.out.println("Enter the horizontal coordinate of the first point:");
	    s = scanner.nextInt();
	    System.out.println("Enter the vertical coordinate of the first point:");
	    p = scanner.nextInt();
	    System.out.println("Enter the length (change in x):");
	    length = scanner.nextInt();
	    System.out.println("Enter the width (change in y):");
	    width = scanner.nextInt();
	    try {
		c.eraseRectangle(s,p,depth,length,width,orientation);
		erasedIndeed(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("eraseRectangle: out of bounds");
		erasedIndeed(false);
	    }

	} else 	if (answer.equals("rectangular prism")) {
	    int s,p,q,length,width,height;
	    System.out.println("Enter the x-coordinate of the first point:");
	    s = scanner.nextInt();
	    System.out.println("Enter the y-coordinate of the first point:");
	    p = scanner.nextInt();
	    System.out.println("Enter the z-coordinate of the first point:");
	    q = scanner.nextInt();
	    System.out.println("Enter the length (change in x):");
	    length = scanner.nextInt();
	    System.out.println("Enter the width (change in y):");
	    width = scanner.nextInt();
	    System.out.println("Enter the height (change in z):");
	    height = scanner.nextInt();
	    try {
		c.eraseRectangularPrism(s,p,q,length,width,height);
		erasedIndeed(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("eraseRectangularPrism: out of bounds");
		erasedIndeed(false);
	    }

	} else 	if (answer.equals("rectangular pyramid")) {
	    int s,p,baselayer,length,width;
	    String orientation;
	    System.out.println("Enter the orientation of the pyramid (top, front, or side):");
	    orientation = scanner.next();
	    System.out.println("Enter the depth at which the base of the pyramid will lie:");
	    baselayer = scanner.nextInt();
	    System.out.println("Enter the horizontal coordinate of the first point:");
	    s = scanner.nextInt();
	    System.out.println("Enter the vertical coordinate of the first point:");
	    p = scanner.nextInt();
	    System.out.println("Enter the length (change in x) of the base:");
	    length = scanner.nextInt();
	    System.out.println("Enter the width (change in y) of the base:");
	    width = scanner.nextInt();
	    System.out.println("The pyramid's height will be determined based on its base.");
	    try {
		c.eraseRectangularPyramid(s,p,baselayer,length,width,orientation);
		erasedIndeed(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("eraseRectangularPyramid: out of bounds");
		erasedIndeed(false);
	    }

	} else 	if (answer.equals("triangle")) {
	    int s,p,depth,height;
	    String orientation;
	    System.out.println("Enter the orientation of the triangle (top, front, or side):");
	    orientation = scanner.next();
	    System.out.println("Enter the depth at which the triangle should be inserted from the view you chose:");
	    depth = scanner.nextInt();
	    System.out.println("Enter the x-coordinate of the top point:");
	    s = scanner.nextInt();
	    System.out.println("Enter the y-coordinate of the top point:");
	    p = scanner.nextInt();
	    System.out.println("Enter the height of the triangle:");
	    height = scanner.nextInt();
	    System.out.println("The triangle's base length will be determined based on its height.");
	    try {
		c.eraseTriangle(s,p,depth,height,orientation);
		erasedIndeed(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("eraseTriangle: out of bounds");
		erasedIndeed(false);
	    }

	} else 	if (answer.equals("triangular prism")) {
	    int s,p,baselayer,triangleheight,prismheight;
	    String orientation;
	    System.out.println("Enter the orientation of the prism (top, front, or side):");
	    orientation = scanner.next();
	    System.out.println("Enter the depth at which the base of the prism will lie:");
	    baselayer = scanner.nextInt();
	    System.out.println("Enter the horizontal coordinate of the top point of the base:");
	    s = scanner.nextInt();
	    System.out.println("Enter the vertical coordinate of the top point of the base:");
	    p = scanner.nextInt();
	    System.out.println("Enter the height of the base:");
	    triangleheight = scanner.nextInt();
	    System.out.println("The pyramid's base length will be determined based on its height.");
	    System.out.println("Enter the height of the prism:");
	    prismheight = scanner.nextInt();
	    try {
		c.eraseTriangularPrism(s,p,baselayer,triangleheight,prismheight,orientation);
		erasedIndeed(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("eraseTriangularPrism: out of bounds");
		erasedIndeed(false);
	    }

	} else 	if (answer.equals("triangular pyramid")) {
	    int s,p,baselayer,triangleheight;
	    String orientation;
	    System.out.println("Enter the orientation of the pyramid (top, front, or side):");
	    orientation = scanner.next();
	    System.out.println("Enter the depth at which the base of the pyramid will lie:");
	    baselayer = scanner.nextInt();
	    System.out.println("Enter the horizontal coordinate of the top point of the base:");
	    s = scanner.nextInt();
	    System.out.println("Enter the vertical coordinate of the top point of the base:");
	    p = scanner.nextInt();
	    System.out.println("Enter the height of the triangle:");
	    triangleheight = scanner.nextInt();
	    System.out.println("The triangle's base length will be determined based on its height.");
	    System.out.println("The pyramid's height will be determined based on its base.");
	    try { 
		c.eraseTriangularPyramid(s,p,baselayer,triangleheight,orientation);
		erasedIndeed(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("eraseTriangularPyramid: out of bounds");
		erasedIndeed(false);
	    }

	} else 	if (answer.equals("circle")) {
	    int h,k,radius,depth;
	    String orientation;
	    System.out.println("Enter the orientation of the circle (top, front, or side):");
	    orientation = scanner.next();
	    System.out.println("Enter the depth at which the circle should be inserted from the view you chose:");
	    depth = scanner.nextInt();
	    System.out.println("Enter the horizontal coordinate of the center point:");
	    h = scanner.nextInt();
	    System.out.println("Enter the vertical coordinate of the center point:");
	    k = scanner.nextInt();
	    System.out.println("Enter the radius of the circle:");
	    radius = scanner.nextInt();
	    try {
		c.eraseCircle(h,k,radius,depth,orientation);	    
		erasedIndeed(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("eraseCircle: out of bounds");
		erasedIndeed(false);
	    }
		
	} else 	if (answer.equals("cylinder")) {
	    int h,k,baselayer,radius,height;
	    String orientation;
	    System.out.println("Enter the orientation of the cylinder (top, front, or side):");
	    orientation = scanner.next();
	    System.out.println("Enter the depth at which the base of the cylinder will lie:");
	    baselayer = scanner.nextInt();
	    System.out.println("Enter the horizontal coordinate of the center point of the base:");
	    h = scanner.nextInt();
	    System.out.println("Enter the vertical coordinate of the center point of the base:");
	    k = scanner.nextInt();
	    System.out.println("Enter the radius:");
	    radius = scanner.nextInt();
	    System.out.println("Enter the height of the cylinder:");
	    height = scanner.nextInt();
	    try {
		c.eraseCylinder(h,k,baselayer,radius,height,orientation);
		erasedIndeed(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("eraseCylinder: out of bounds");
		erasedIndeed(false);
	    }

	} else 	if (answer.equals("cone")) {
	    int h,k,radius,baselayer;
	    String orientation;
	    System.out.println("Enter the orientation of the cone (top, front, or side):");
	    orientation = scanner.next();
	    System.out.println("Enter the depth at which the base of the cone will lie:");
	    baselayer = scanner.nextInt();
	    System.out.println("Enter the horizontal coordinate of the center point of the base:");
	    h = scanner.nextInt();
	    System.out.println("Enter the vertical coordinate of the center point of the base:");
	    k = scanner.nextInt();
	    System.out.println("Enter the radius of the base:");
	    radius = scanner.nextInt();
	    System.out.println("The cone's height will be determined based on its base.");
	    try {
		c.eraseCone(h,k,radius,baselayer,orientation);
		erasedIndeed(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("eraseCone: out of bounds");
		erasedIndeed(false);
	    }

	} else 	if (answer.equals("sphere")) {
	    int s,p,q,radius;
	    System.out.println("Enter the x-coordinate of the center point of the sphere:");
	    s = scanner.nextInt();
	    System.out.println("Enter the y-coordinate of the center point of the sphere:");
	    p = scanner.nextInt();
	    System.out.println("Enter the z-coordinate of the center point of the sphere:");
	    q = scanner.nextInt();
	    System.out.println("Enter the radius:");
	    radius = scanner.nextInt();
	    try {
		c.eraseSphere(s,p,q,radius);
		erasedIndeed(true);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("eraseSphere: out of bounds");
		erasedIndeed(false);
	    }

	} else {
	    System.out.println("erase: Invalid input.  Try again.");
	    erase();

	}

    }


    public void erasedIndeed(boolean b) {

	if (b)
	    System.out.println("Your shape was successfully removed from the grid.");
	else
	    System.out.println("Your shape was not successfully removed from the grid.");
	menu();

    }


    public void view() {
	System.out.println();
	System.out.println("What would you like to view?");
	System.out.println("\n specific layer \n whole grid");
	System.out.println();
	answer = scanner.nextLine();
	if (answer.equals("specific layer") || answer.equals("specific") || answer.equals("layer")) {
	    System.out.println("From which orientation would you like to choose the layer depth?  (Type \"top,\" \"front,\" or \"side.\")");
	    System.out.println();
	    answer = scanner.next();
	    if (answer.equals("top")) {
		System.out.println("How deep into the grid?");
		int i = scanner.nextInt();
		c.viewLayer(i,answer);
	    } else if (answer.equals("front")) {
		System.out.println("How deep into the grid?");
		int i = scanner.nextInt();
		c.viewLayer(i,answer);
	    } else if (answer.equals("side")) {
		System.out.println("How deep into the grid?");
		int i = scanner.nextInt();
		c.viewLayer(i,answer);
	    } else {
		System.out.println("view: Invalid input.  Try again.");
		view();
	    }
	} else if (answer.equals("whole grid") || answer.equals("whole") || answer.equals("grid")) {
	    System.out.println("From which orientation would you like to view the grid?  (Type \"top,\" \"front,\" or \"side.\")");
	    System.out.println();
	    answer = scanner.next();
	    if (answer.equals("top")) {
		c.viewAllLayers(answer);
	    } else if (answer.equals("front")) {
		c.viewAllLayers(answer);
	    } else if (answer.equals("side")) {
		c.viewAllLayers(answer);
	    } else {
		System.out.println("view: Invalid input.  Try again.");
		view();
	    }
	} else {
	    System.out.println("view: Invalid input.  Try again.");
	    view();
	}

	menu();

    }


    public void changeCharacters() {
	char v=c.getVoidChar(), f=c.getFilledChar();
	System.out.println();
	System.out.println("Would you like to change the character which represents a filled space, an unfilled space, or both?  (Type \"filled,\" \"unfilled,\" or \"both\")");
	System.out.println();
	answer = scanner.next();
	if (answer.equals("filled")) {
	    System.out.println("Enter the character you want to represent a filled space:");
	    f = scanner.next().charAt(0);
	} else if (answer.equals("unfilled")) {
	    System.out.println("Enter the character you want to represent an unfilled space:");
	    v = scanner.next().charAt(0);    
	} else if (answer.equals("both")) {
	    System.out.println("Enter the character you want to represent a filled space:");
	    f = scanner.next().charAt(0);
	    System.out.println("Enter the character you want to represent an unfilled space:");
	    v = scanner.next().charAt(0);
 	} else {
	    System.out.println("changeCharacters: Invalid input.  Try again.");
	    changeCharacters();
	}

	c.setChars(v,f);
	System.out.println("Your characters are now \n filled: '"+f+"'\n unfilled: '"+v+"'");
	menu();

    }


}
