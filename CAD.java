import java.io.*;
import java.util.*;

//xmax==grid.length==templatetop.length==templatefront.length
//ymax==grid[0].length==templatetop[0].length==templateside.length
//zmax==grid[0][0].length==templatefront[0].length==templateside[0].length

//Fixed angles for shapes:
//  Triangles: right isosceles, hypoteneuse is the base.
//  Pyramids and cone: each tapering layer is smaller than the previous by a one-unit thick perimeter.  (The shape is drawn starting from the base; each layer is drawn with decreasing size until that size becomes fractional.)

public class CAD {


    private char[][][] grid;
    private char voidchar, filledchar;
    private int xmax, ymax, zmax;


    public CAD() {
	xmax=100;
	ymax=100;
	zmax=100;
	voidchar='.';
	filledchar='O';
	init();
    }


    public CAD(char v, char f) {
	xmax=100;
	ymax=100;
	zmax=100;
	voidchar=v;
	filledchar=f;
	init();	
    }


    public CAD(int x, int y, int z) {
	xmax=x;
	ymax=y;
	zmax=z;
	voidchar='.';
	filledchar='O';
	init();
    }


    public CAD(int x, int y, int z, char v, char f) {
	xmax=x;
	ymax=y;
	zmax=z;
	voidchar=v;
	filledchar=f;
	init();
    }


    public void init() {
	grid = new char[xmax][ymax][zmax];
	initArray(grid);
    }


    public void initArray(char[][][] c) {
	for(int i=0;i<c.length;i++){
	    for(int j=0;j<c[0].length;j++){
		for(int k=0;k<c[0][0].length;k++)
		    c[i][j][k]=voidchar;
	    }
	}
    }


    public char getVoidChar() {return voidchar;}
    public char getFilledChar() {return filledchar;}
    public void setChars(char v, char f) {
	for(int i=0;i<grid.length;i++) {
	    for(int j=0;j<grid[0].length;j++) {
		for(int k=0;k<grid[0][0].length;k++) {
		    if(grid[i][k][j]==voidchar)
			grid[i][k][j]=v;
		    else
			grid[i][k][j]=f;
		}
	    }
	}
	voidchar = v;
	filledchar = f;
    }


    public char[][] templateForOrientation(String orientation, boolean filled) {
	char[][] template;
	if (orientation.equals("top")) {
	    template = new char[grid.length][grid[0].length];
	}
	else if (orientation.equals("front")) {
	    template = new char[grid.length][grid[0][0].length];
	}
	else if (orientation.equals("side")) {
	    template = new char[grid[0].length][grid[0][0].length];
	}
	else {
	    System.out.println("invalid input");
	    template = new char[0][0]; //Courtesy of the all-knowing Java compiler.
	}
	for (int i=0;i<template.length;i++) {
	    for (int j=0;j<template[0].length;j++) {
		if (filled)
		    template[i][j]=filledchar;
		else
		    template[i][j]=voidchar;
	    }
	}
	return template;
    }


    public void makePoint(int x, int y, int z) {
	grid[x][y][z]=filledchar;
    }


    public void makeLine(int s, int p, int depth, int length, String orientation) {
	makeRectangle(s,p,depth,length,1,orientation);
    }


    public void makeRectangle(int s, int p, int depth, int length, int width, String orientation) {
	char[][] template = templateForOrientation(orientation,false);
	if(length>=0&&width>=0) {
	    for(int i=s; i<s+length; i++) {
		for(int j=p; j<p+width; j++)
		    template[i][j]=filledchar;
	    }
	} else
	    System.out.println("makeRectangle: negative length and/or width");
	insertInGrid(template,orientation,depth,true);
    }


    public void makeRectangularPrism(int s, int p, int q, int length, int width, int height) {
	for(int i=q;i<q+height;i++)
	    makeRectangle(s,p,i,length,width,"top");
    }


    public void makeRectangularPyramid(int s, int p, int baselayer, int length, int width, String orientation) { 
	int height=findHeight(length, width);
	int currentLayer=baselayer;   
	while (height!=0) {
	    makeRectangle(s, p, baselayer, length, width, orientation);
	    length = length - 2;
	    width = width - 2;
	    s++;
	    p++;
	    baselayer++;
	    height--;
	}
    }  


    public void makeTriangle(int s, int p, int depth, int height, String orientation) {
	char[][] template = templateForOrientation(orientation,false);
	if(height>=0) {
	    int lowboundary=s;
	    int highboundary=s;
	    for(int i=p;i<=p+height-1;i++) {
		for(int j=s-height+1;j<=s+height-1;j++) {
		    if (j>=lowboundary&&j<=highboundary)
			template[i][j]=filledchar;
		}
		lowboundary--;
		highboundary++;
	    }
	} else {
	    System.out.println("makeTriangle: negative height");
	}
	insertInGrid(template,orientation,depth,true);
    }    


    public void makeTriangularPrism(int s, int p, int baselayer, int triangleheight, int prismheight, String orientation) {
    	for(int i=baselayer;i<baselayer+prismheight;i++)
    	    makeTriangle(s,p,i,triangleheight,orientation);
    }


    public void makeTriangularPyramid(int s, int p, int baselayer, int triangleheight, String orientation) {
    	int pyramidheight=findHeight(findWidth(triangleheight),triangleheight);
    	int currentLayer=baselayer;
    	while (pyramidheight!=0) {
    	    makeTriangle(s, p, baselayer, triangleheight, orientation);
    	    s++;
    	    p++;
    	    baselayer++;
    	    pyramidheight--;
    	}
    }


    public void makeCircle(int h, int k, int radius, int depth, String orientation) {
    	char[][] template = templateForOrientation(orientation,false);
    	for (int i=0;i<template.length;i++){
    	    for (int j=0;j<template[i].length;j++){
    		if (find2DDistance(h,k,i,j)<=radius)
    		    template[i][j]=filledchar;
    	    }
    	}
    	insertInGrid(template,orientation,depth,true);
    }


    public void makeCylinder(int h, int k, int baselayer, int radius, int height, String orientation) {
    	for(int i=baselayer;i<baselayer+height;i++)
    	    makeCircle(h,k,radius,i,orientation);
    }


    public void makeCone(int h, int k, int radius, int baselayer, String orientation) { 
    	int height=radius;
    	for(int i=baselayer;i<baselayer+height;i++) {
    	    makeCircle(h,k,radius,i,orientation);
    	    radius--;
    	}
    }


    public void makeSphere(int s, int p, int q, int radius) {
    	for(int i=0;i<grid.length;i++) {
    	    for(int j=0; j<grid[0].length;j++) {
    		for(int k=0;k<grid[0][0].length;k++) {
    		    if(find3DDistance(i,s,j,p,k,q)<=radius)
    			grid[i][j][k]=filledchar;
    		}
    	    }
    	}
    }


    public void erasePoint(int x, int y, int z) {
    	grid[x][y][z]=voidchar;
    }


    public void eraseLine(int s, int p, int depth, int length, String orientation) {
    	eraseRectangle(s,p,depth,length,1,orientation);
    }


    public void eraseRectangle(int s, int p, int depth, int length, int width, String orientation) {
    	char[][] template = templateForOrientation(orientation,true);
    	if(length>=0&&width>=0) {
    	    for(int i=s; i<s+length; i++) {
    		for(int j=p; j<p+width; j++)
    		    template[i][j]=voidchar;
    	    }
    	} else
    	    System.out.println("eraseRectangle: negative length and/or width");
    	insertInGrid(template,orientation,depth,false);
    }


    public void eraseRectangularPrism(int s, int p, int q, int length, int width, int height) {
    	for(int i=q;i<q+height;i++)
         makeRectangle(s,p,i,length,width,"top");
    	    }


    public void eraseRectangularPyramid(int s, int p, int baselayer, int length, int width, String orientation) { 
    	int height=findHeight(length, width);
    	int currentLayer=baselayer;   
    	while (height!=0) {
    	    eraseRectangle(s, p, baselayer, length, width, orientation);
    	    length = length - 2;
    	    width = width - 2;
    	    s++;
    	    p++;
    	    baselayer++;
    	    height--;
    	}
    }  


    public void eraseTriangle(int s, int p, int depth, int height, String orientation) {
    	char[][] template = templateForOrientation(orientation,true);
    	if(height>=0) {
    	    int lowboundary=s;
    	    int highboundary=s;
    	    for(int i=p;i<=p+height-1;i++) {
    		for(int j=s-height+1;j<=s+height-1;j++) {
    		    if (j>=lowboundary&&j<=highboundary)
    			template[i][j]=voidchar;
    		}
    		lowboundary--;
    		highboundary++;
    	    }
    	} else {
    	    System.out.println("eraseTriangle: negative height");
    	}
    	insertInGrid(template,orientation,depth,false);
    }    


    public void eraseTriangularPrism(int s, int p, int baselayer, int triangleheight, int prismheight, String orientation) {
    	for(int i=baselayer;i<baselayer+prismheight;i++)
    	    eraseTriangle(s,p,i,triangleheight,orientation);
    }


    public void eraseTriangularPyramid(int s, int p, int baselayer, int triangleheight, String orientation) {
    	int pyramidheight=findHeight(findWidth(triangleheight),triangleheight);
    	int currentLayer=baselayer;
    	while (pyramidheight!=0) {
    	    eraseTriangle(s, p, baselayer, triangleheight, orientation);
    	    s++;
    	    p++;
    	    baselayer++;
    	    pyramidheight--;
    	}
    }


    public void eraseCircle(int h, int k, int radius, int depth, String orientation) {
    	char[][] template = templateForOrientation(orientation,true);
    	for (int i=0;i<template.length;i++){
    	    for (int j=0;j<template[i].length;j++){
    		if (find2DDistance(h,k,i,j)<=radius)
    		    template[i][j]=voidchar;
    	    }
    	}
    	insertInGrid(template,orientation,depth,false);
    }


    public void eraseCylinder(int h, int k, int baselayer, int radius, int height, String orientation) {
    	for(int i=baselayer;i<baselayer+height;i++)
    	    eraseCircle(h,k,radius,i,orientation);
    }


    public void eraseCone(int h, int k, int radius, int baselayer, String orientation) { 
    	int height=radius;
    	for(int i=baselayer;i<baselayer+height;i++) {
    	    eraseCircle(h,k,radius,i,orientation);
    	    radius--;
    	}
    }


    public void eraseSphere(int s, int p, int q, int radius) {
    	for(int i=0;i<grid.length;i++) {
    	    for(int j=0; j<grid[0].length;j++) {
    		for(int k=0;k<grid[0][0].length;k++) {
    		    if(find3DDistance(i,s,j,p,k,q)<=radius)
    			grid[i][j][k]=voidchar;
    		}
    	    }
    	}
    }


    public int findHeight(int length, int width){
	int result = 0;
	while (length > 0 && width > 0){
	    length = length - 2;
	    width = width - 2;
	    result++;
	}
	return result;
    }

   
    public int findWidth(int height) {
	int result=0;
	while (height > 0){
	    result+=2;
	    height--;
	}
	return result;
    }


    public double find2DDistance (double x1, double y1, double x2, double y2){
	return Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
    }


    public double find3DDistance(int x1, int x2, int y1, int y2, int z1, int z2) {
	return(Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)+(z2-z1)*(z2-z1)));
    }


    public void insertInGrid(char[][] c, String orientation, int depth, boolean toAdd) {
	if (toAdd) {
	    if (orientation.equals("top")) {
		for(int i=0;i<grid.length;i++) {
		    for(int j=0;j<grid[0].length;j++){
			if(c[i][j]==filledchar)
			    grid[i][j][depth]=filledchar;
		    }
		}
	    } else if (orientation.equals("front")){
		for(int i=0;i<grid.length;i++) {
		    for(int k=0;k<grid[0][0].length;k++){
			if(c[i][k]==filledchar)
			    grid[i][depth][k]=filledchar;
		    }
		}
	    } else if (orientation.equals("side")){
		for(int j=0;j<grid[0].length;j++) {
		    for(int k=0;k<grid[0][0].length;k++){
			if(c[j][k]==filledchar)
			    grid[depth][j][k]=filledchar;
		    }
		}
	    } else {
		System.out.println("insertInGrid: invalid orientation input");
	    }
	} else {
	    if (orientation.equals("top")) {
		for(int i=0;i<grid.length;i++) {
		    for(int j=0;j<grid[0].length;j++){
			if(c[i][j]==voidchar)
			    grid[i][j][depth]=voidchar;
		    }
		}
	    } else if (orientation.equals("front")){
		for(int i=0;i<grid.length;i++) {
		    for(int k=0;k<grid[0][0].length;k++){
			if(c[i][k]==voidchar)
			    grid[i][depth][k]=voidchar;
		    }
		}
	    } else if (orientation.equals("side")){
		for(int j=0;j<grid[0].length;j++) {
		    for(int k=0;k<grid[0][0].length;k++){
			if(c[j][k]==voidchar)
			    grid[depth][j][k]=voidchar;
		    }
		}
	    } else {
		System.out.println("insertInGrid: invalid orientation input");
	    }
	}
    }


    public void viewLayer(int depth, String orientation) {
	System.out.println();
	if (orientation.equals("top")) {
	    if (depth >= grid.length) {
		System.out.println("viewLayer: invalid depth input");
	    } else {
		for(int i=0;i<grid.length;i++) {
		    for(int j=0;j<grid[0].length;j++)
			System.out.print(grid[i][j][depth]);
		    System.out.println();
		}
	    }
	} else if (orientation.equals("front")){
	    if (depth >= grid[0].length) {
		System.out.println("viewLayer: invalid depth input");
	    } else {
		for(int i=0;i<grid.length;i++) {
		    for(int k=0;k<grid[0][0].length;k++)
			System.out.print(grid[i][depth][k]);
		    System.out.println();
		}
	    }
	} else if (orientation.equals("side")){
	    if (depth >= grid[0][0].length) {
		System.out.println("viewLayer: invalid depth input");
	    } else {
		for(int j=0;j<grid[0].length;j++) {
		    for(int k=0;k<grid[0][0].length;k++)
			System.out.print(grid[depth][j][k]);
		    System.out.println();
		}
	    }
	} else {
	    System.out.println("viewLayer: invalid orientation input");
	}
    }


    public void viewAllLayers() {
	System.out.println();
	for(int k=0;k<grid[0][0].length;k++) {
	    viewLayer(k,"top");
	    System.out.println();
	}
    }


    public void viewAllLayers(String orientation) {
	System.out.println();
	if (orientation.equals("top")) {
	    for(int k=0;k<grid[0][0].length;k++) {
		viewLayer(k,"top");
		System.out.println();
	    }
	} else if (orientation.equals("front")){
	    for(int j=0;j<grid[0].length;j++) {
		viewLayer(j,"front");
		System.out.println();
	    }
	} else if (orientation.equals("side")){
	    for(int i=0;i<grid.length;i++) {
		viewLayer(i,"side");
		System.out.println();
	    }
	} else {
	    System.out.println("viewAllLayers: invalid orientation input");
	}
    }


    public void clearAllLayers() {
	for(int i=0;i<grid.length;i++){
	    for(int j=0;j<grid[0].length;j++){
		for(int k=0;k<grid[0][0].length;k++)
		    grid[i][j][k]=voidchar;
	    }
	}
    }


}
