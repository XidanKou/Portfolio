/*
  File: rectangles.cpp
  Created by: Xidan Kou
  Creation Date: 12/1/2019
  Synopsis: print values of rectangles and scale them by 3 and output again 
*/
#include <iostream>
#include <string>
#include <vector>
using namespace std;
class Point
{
private:
double px;
double py;
public:
void setX(const double x);
void setY(const double y);
double getX() const;
double getY() const;
};
class Rectangle
{
private:
string name;
Point blPoint;
double length, height;
public:
// member functions
void setName(const string & inName);
void setBottomLeft(const double x, const double y);
void setDimensions(const double inLength, const double inHeight);
string getName() const;
Point getBottomLeft() const;
double getLength() const;
double getHeight() const;
double area() const;
double perimeter() const;
Point midPoint() const;
void scaleBy3();
void display() const;
};
void welcome();
bool check_valid(string & ask_name,string & error, string & name_used, string & 
name,vector<Rectangle> list);
void coordinate(string & prompt,double & x, double & y);
void dimension (string & prompt, double & height,double & length);
void adds(string name, double x,double y, double length, double height, 
vector<Rectangle> & list);
void displays(vector<Rectangle>list);
// FUNCTION PROTOTYPES GO HERE:
Ghias Padmakoesoema (padmakoesoema.2)
-5, Missing function comments.
int main()
{
// Define your local variables, e.g. a vector of class Rectangle
 
  string name; // name user entered include rec
 vector<Rectangle> list;
 // declear the vector of class Rectangle
  Rectangle rec;
 double x,y,length,height;// declear x and y coordinate, length and height of 
rectangle
 string prompt1, prompt2;
 bool check;
// Display welcome banner
 welcome();
/* Prompt user for first rectangle or 'stop' */
  cout<<endl;
  string ask_name ="Enter the name of the first rectangle: "; 
  string  error = "Invalid input. Type 'rec' followed by the name or 'stop' if 
done. ";
  string  name_used = " This name is already being used!";
  // the prompt will be used later
  check = check_valid(ask_name,error,name_used,name, list);  
  while(check==false)// WHILE user input is invalid
    {
      check = check_valid(ask_name,error,name_used,name, list);
      // check valid again
    }
// IF user input is not 'stop'
  if((name.substr(0,4)!="stop"))
    {
      rec.setName(name.substr(4,name.length()-1));
     // Extract rectangle name from user input
// Prompt for bottom left point
      prompt1 = "Enter " + rec.getName() +"'s bottom left x and y coords: ";
      prompt2 = "Enter "+rec.getName()+"'s length and height: ";
      coordinate( prompt1, x, y);
 
// Prompt for length and height
      dimension ( prompt2, height,length);
      rec.setDimensions(length,height);
// Add rectangle to the rectangle list
      adds(rec.getName(), x,y, length, height, list);
      getline(cin,name);//avoid run time error, to get rid of the line
ask_name =" Thank you! Enter the name of the next rectangle: ";  // WHILE 
user input is invalid
        check = check_valid(ask_name,error,name_used,name, list);
while(check==false) 
// WHILE user input is invalid
    {
       check = check_valid(ask_name,error,name_used,name, list);
    }  
        
    }
/* Prompt user for next rectangle or 'stop' */
// Display "Thank you! "
 
// WHILE user input is not 'stop'
  while((name.substr(0,4)!="stop"))
    {
                   rec.setName(name.substr(4,name.length()-1));
// Extract rectangle name from user input
// Prompt for bottom left point
   coordinate( prompt1, x, y);
 rec.setBottomLeft(x,y); 
// Prompt for length and height
      dimension ( prompt2, height,length);
     
// Add rectangle to the rectangle list
 adds(rec.getName(), x,y, length, height, list);
 getline(cin,name);//avoid run time error, to get rid of the line
ask_name =" Thank you! Enter the name of the next rectangle: ";  // WHILE 
user input is invalid
        check = check_valid(ask_name,error,name_used,name, list);
  while(check==false)// WHILE user input is invalid
    {
       check = check_valid(ask_name,error,name_used,name, list);
    }  
        
                 
    }
// IF the rectangle list is not empty
  if(list.size()!=0)
    {
      displays(list);
// Display all rectangles in the rectangle list
    }
// ELSE 
  else
    {
      cout<<"You have no rectangles in your list."<<endl;
// Display that no rectangles are in the list
    }
  return 0;
}
 
// FUNCTION DEFINITIONS GO HERE:
void welcome()
{
  //display welcome  banner
cout<<"Welcome! Create your own list of rectangles."<<endl<<"You will be asked to 
provide information about each rectangle in your list by name."<<endl<<"Type the 
word 'stop' for the rectangle name when you are done."<<endl;
}
bool check_valid(string & ask_name,string & error, string & name_used, string & 
name,vector<Rectangle> list)
{ // ask user to enter the name and check its validity
    cout<<ask_name;
 getline(cin, name);
 
// WHILE user input is invalid
 if((name.substr(0,4)!="rec ")&& (name.substr(0,4)!="stop")) // check if input is 
valid
    {
      cout<<error<<endl<<"Try again! " << ask_name;
    getline(cin,name);
      return false;     
      
    }
  
      
    else
      {
for(int i=0;i<list.size();i++) // check if name has been used
   {
    
     if(name.substr(4,name.length()-4)==list[i].getName())
       {
 cout<<name_used<<endl<<"Try again! "<<ask_name;
 return false;
       }
   }
      }
 return true;
}
void coordinate(string & prompt,double & x, double & y)
{// get x y value
  cout<<prompt;
      cin>>x>>y;
     
}
void dimension (string & prompt, double & height,double & length)
{ // get height and length
 
 cout<<prompt;
      cin>> length>>height;
     
}
void adds(string name, double x,double y, double length, double height, 
vector<Rectangle> & list)
{
  // adds name height length to the vector
  Rectangle rec;
  rec.setName(name);
rec.setBottomLeft(x,y);
rec.setDimensions(length,height);provide information about each rectangle in your list by name."<<endl<<"Type the 
word 'stop' for the rectangle name when you are done."<<endl;
}

bool check_valid(string & ask_name,string & error, string & name_used, string & 
name,vector<Rectangle> list)
{ // ask user to enter the name and check its validity
    cout<<ask_name;
 getline(cin, name);
 
// WHILE user input is invalid
 if((name.substr(0,4)!="rec ")&& (name.substr(0,4)!="stop")) // check if input is 
valid
    {
      cout<<error<<endl<<"Try again! " << ask_name;
    getline(cin,name);
      return false;     
      
    }
  
      
    else
      {
for(int i=0;i<list.size();i++) // check if name has been used
   {
    
     if(name.substr(4,name.length()-4)==list[i].getName())
       {
 cout<<name_used<<endl<<"Try again! "<<ask_name;
 return false;
       }
   }
      }
 return true;
}
void coordinate(string & prompt,double & x, double & y)
{// get x y value
  cout<<prompt;
      cin>>x>>y;
     
}
void dimension (string & prompt, double & height,double & length)
{ // get height and length
 
 cout<<prompt;
      cin>> length>>height;
     
}
void adds(string name, double x,double y, double length, double height, 
vector<Rectangle> & list)
{
  // adds name height length to the vector
  Rectangle rec;
  rec.setName(name);
rec.setBottomLeft(x,y);
rec.setDimensions(length,height);
Ghias Padmakoesoema (padmakoesoema.2)
-1, Need to validate length & height > 0.
      list.push_back(rec);
}
void displays(vector<Rectangle>list)
{
  // display the whole information
  for(int i=0; i<list.size();i++)
    {
      cout<<"Rectangle '"<< list[i].getName()<<"' : ";
      list[i].display();
      list[i].scaleBy3();
      cout<<"     After scale by 3:";
      list[i].display();//display information after scaled by three
    }
}
 
// CLASS MEMBER FUNCTION DEFINITINOS GO HERE:
void Point::setX(const double x) 
{
px = x;
}
void Point::setY(const double y) 
{
py = y;
}
double Point::getX() const 
{
return (px);
}
double Point::getY() const 
{
return (py);
}
void Rectangle::setName(const string & inName) 
{
  name = inName; // replace with your code
}
void Rectangle::setBottomLeft(const double x, const double y) 
{
  blPoint.setX(x);
  blPoint.setY(y); // replace with your code
}
void Rectangle::setDimensions(const double inLength, const double inHeight) 
{
  length = inLength;
  height = inLength; // replace with your code
}
string Rectangle::getName() const
{
  return name; // replace with your code
Ghias Padmakoesoema (padmakoesoema.2)
-1, Should be inHeight.
}
Point Rectangle::getBottomLeft() const
{
  return blPoint; // replace with your code
}
double Rectangle::getLength() const
{
  return length; // replace with your code
}
double Rectangle::getHeight() const
{
  return height; // replace with your code
}
double Rectangle::area() const
{ //get area
  double area;
  area = length * height;
  return area; // replace with your code
}
double Rectangle::perimeter() const
{//get perimeter
  double p;
  p = 2*length + 2*height;
  return p; // replace with your code
}
Point Rectangle::midPoint() const
{//get midpoint
  Point midP;
  midP.setX(blPoint.getX()+length/2.0);
 midP.setY(blPoint.getY()+height/2.0);
 return midP; // replace with your code
}
void Rectangle::scaleBy3() 
{// scale all the values by three
  setDimensions(length*3.0,height*3.0);
  blPoint.setX(midPoint().getX()-length/2.0);
  blPoint.setY(midPoint().getY()-length/2.0); // replace with your code
}
void Rectangle::display() const
{
  // display its location, midpoint,area, and perimeter
  cout<<"Location is ("<<getBottomLeft().getX()<<", "<<getBottomLeft().getY()<<"), 
Length is "<< getHeight()<<", Height is "<<getHeight()<<"; Area is "<< area()<<", 
Perimeter is "<<perimeter()<<", Midpoint is located at "<<midPoint().getX()<<", 
"<<midPoint().getY()<<")"<<endl;
 
  }