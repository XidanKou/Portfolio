/*
  File: vector2D.cpp
  Created by: Xidan Kou
  Creation Date: 24/10/2019
  Synopsis: Add, suntract, multiplie two vectors 
*/
#include <iostream>
#include <iomanip>
#include <cmath>
using namespace std;
const double EPSILON(1e-12);
// function prototypes
// ENTER FUNCTION PROTOTYPE FOR read_vector HERE.
void read_vector (const string & READ, double & x, double & y);
// ENTER FUNCTION PROTOTYPE FOR vector_length HERE.
double vector_length (double x, double y);
// ENTER FUNCTION PROTOTYPE FOR write_vector HERE.
void write_vector(const string & display, double x, double y);
// ENTER FUNCTION PROTOTYPE FOR vector_add HERE.
  void vector_add (double x1,double y1, double x2,double y2, double & x3, double& 
y3 );
// ENTER FUNCTION PROTOTYPE FOR vector_subtract HERE.
void vector_subtract (double x1,double y1, double x2,double y2,double & x3, double 
& y3);
// ENTER FUNCTION PROTOTYPE FOR scalar_mult HERE.
void scalar_mult(double x1,double y1,double s,double & x2, double & y2);
// ENTER FUNCTION PROTOTYPE FOR normalize HERE.
void normalize(double &x, double & y);
// ENTER FUNCTION PROTOTYPE FOR perpendicular HERE.
void perpendicular(double x1, double y1, double x2, double y2);
// *** DO NOT CHANGE ANY CODE IN THE MAIN FUNCTION.
int main()
{
  double u1, v1; // coordinates of first vector
  double u2, v2; // coordinates of second vector
  double u3, v3;
  double scalar;
  read_vector("Enter first vector (2 floats): ", u1, v1);
  read_vector("Enter second vector (2 floats): ", u2, v2);
  
  cout << "Enter scalar multiplier: ";
  cin >> scalar;
  cout << endl;
  write_vector("First vector: ", u1, v1);
  write_vector("Second vector: ", u2, v2);
  cout << endl;
  
  vector_add(u1, v1, u2, v2, u3, v3);
  write_vector("Vector add: ", u3, v3);
  vector_subtract(u1, v1, u2, v2, u3, v3);
  write_vector("Vector subtract: ", u3, v3);
  scalar_mult(u1, v1, scalar, u3, v3);
  write_vector("Scalar multiplier: ", u3, v3);
  cout << endl;
  
  write_vector("First vector: ", u1, v1);
  write_vector("Second vector: ", u2, v2);
  perpendicular(u1, v1, u2, v2);
  
  return(0);
}
// DEFINE FUNCTION read_vector HERE.
void read_vector (const string & READ, double & x, double & y)
{
  cout<< READ;
  cin >> x >> y;
}
// DEFINE FUNCTION vector_length HERE.
double vector_length (double x, double y)
{
  double l;// l = length
  l = sqrt(pow(x,2.0)+pow(y,2.0));
  return l;
}
// DEFINE FUNCTION write_vector HERE.
void write_vector(const string & display, double x, double y)
{
  cout<<display<<"("<<x<<", "<<y<<") has length "<< vector_length(x,y)<<endl;
}
// DEFINE FUNCTION vector_add HERE.
void vector_add (double x1,double y1, double x2,double y2, double & x3, double& 
y3 )
{
  x3 = x1 + x2;
  y3 = y1 + y2;
}
// DEFINE FUNCTION vector_subtract HERE.
void vector_subtract (double x1,double y1, double x2,double y2,double & x3, double 
& y3)
{
  x3 = x1 - x2;
  y3 = y1 - y2;
}
// DEFINE FUNCTION scalar_mult HERE.
void scalar_mult(double x1,double y1,double s,double & x2, double & y2)
{
  x2 = s * x1;
  y2 = s * y1; 
}
// DEFINE FUNCTION normalize HERE.
void normalize(double &x, double & y)
{
  if (abs(vector_length(x,y)-0.0)< EPSILON)
    {
      x = 0.0;
      y = 0.0;
    }
  else{
    x = x / vector_length(x,y);
    y = y / vector_length(x,y);
  }
}
// DEFINE FUNCTION perpendicular HERE.
void perpendicular(double x1, double y1, double x2, double y2)
{
  double vx1,vx2,vy1,vy2,px1,py1,px2,py2;
  /* (vx1,vy1) = unit vector of (x1,y2)
(vx2,vy2) = unit vector of (x2,y2)
(px1,py1)= first perpendicular vector of (vx1,vy1)
(px2,py2)= second perpendicular vector of (vx1,vy1)
   */
  normalize (x1,y1);// normalize x1,y1
  vx1 = x1;
  vy1 = y1;
  normalize (x2,y2);// normalize x2 y2
  vx2 = x2;
  vy2 = y2;
  px1 = -vy1; // first perpendicular vector of unite vector of x1 x2 
  py1 = vx1;
  px2 = -px1;
  py2 = -py1;// second  perpendicular vector of unite vector of x1 x2 
  if((abs(vx2-px1)<EPSILON)&&(abs(vy2-py1)< EPSILON) )// check if unit vector of x2
y2 is same as the first normal vector of x1 y1
    {
      cout<<"Vectors are PERPENDICULAR.";
    }
  else if ((abs(vx2-px2)<EPSILON)&&(abs(vy2-py2)< EPSILON))// check if unit vector 
of x2 y2 is same as the second  normal vector of x1 y1
    {
      cout<<"Vectors are PERPENDICULAR.";
    }
  else 
    {
      cout<<"Vectors are NOT PERPENDICULAR"<<endl;
    }
}