/*
  File: subtraction.cpp
  Created by: Xidan Kou
  Creation Date: 11/1/2019
  Synopsis: playing NIM game
*/
#include <iostream>
#include <iomanip>
using namespace std;
// FUNCTION PROTOTYPES GO HERE:
int read_rods(const int max_rods);
void read_objects(int list_objects[],int n_rods,const int max_objects);
void draw (int list_objects[],int n_rods);
void draw_help(int index, int number_objects, int total_number_objects);
void display_stat(int list_objects[],int n_rods);
void display_help1(int list_objects[], int n_rods);
void display_help2(int list_objects[], int n_rods);
void display_help3(int list_objects[],int n_rods);
bool empty_check( int list_objects[], int n_rods);
void move(int list_objects[], int n_rods, int player, int &index, int 
&n_objects_move);
int move_help1(int player);
void move_help2(int player,int &index, int n_rods);
void move_help3(int list_objects[],int player, int &index );
int move_help4(int index_n_objects, int index);
void remove(int list_objects[], int index, int n_objects_move);
void congratulation (int player);
void change(int & player);
int main()
{
  // Define variables and constants here
  const int max_rods (15);
  const int max_objects(10);
  int  n_rods;
  int * list_objects = new int[max_rods];
  int player =1;
  int index(0), n_objects_move(0);
  // Algorithm:
  
  // Prompt and read number of rods
 n_rods= read_rods(max_rods);
 cout<<endl;
  // Prompt and read the number of objects in each rod
 read_objects(list_objects, n_rods, max_objects);
 cout<<endl;
  // Draw the rods with percentages
Ghias Padmakoesoema (padmakoesoema.2)
-1, Missing 1 required function,
Ghias Padmakoesoema (padmakoesoema.2)
-3, list_objects should be dynamically allocated with the user's input.
 draw(list_objects, n_rods);
 cout<<endl;
  // Display statistics
 display_stat( list_objects, n_rods);
  // WHILE some rod is NOT empty DO
 while(!empty_check(list_objects,n_rods))
   {
// Prompt and read the next player's move
     move( list_objects, n_rods, player, index, n_objects_move);
// Remove the specified number of objects from the specified rod
     remove(list_objects, index, n_objects_move);
   
     // IF all the heaps are empty, THEN
 if(empty_check(list_objects,n_rods))
   {
        // Print a message congratulating the winning player.
     congratulation ( player);
   }
 else 
   {
// ELSE
draw(list_objects, n_rods);
        // Redraw the rods with percentages
display_stat( list_objects, n_rods);
        // Display statistics
 change( player);
// Change to the other player
   }
   
   } // END IF
  // END WHILE
  
  return 0;
}
 
// FUNCTION DEFINITIONS GO HERE:
int read_rods(const int max_rods)
{
  int x;// number of rods
  cout<<"How many rods are in this game ?";
  cin >> x;
  while((x<1) || (x > max_rods)){
    cout<<"Error";
    cin >> x;
  }
  return(x);
}
void read_objects(int list_objects[],int n_rods,const int max_objects)
{
  for(int i=0; i<n_rods;i++)
Ghias Padmakoesoema (padmakoesoema.2)
-1, Need to delete dynamically allocated array.
Ghias Padmakoesoema (padmakoesoema.2)
-2, Error messages should be descriptive and prompt user again.
    {
      cout<<"How many objects are on rod "<<i <<": ";
      cin >>  list_objects[i];
      while( (list_objects[i]<1) || ( list_objects[i])> max_objects)
{
  cout<<"Error";
  cin >> list_objects[i];
}
    }
}
void draw_help(int index, int number_objects, int total_number_objects)
/* index is the array index for this rod
number_objects is the number of objects at that index
total_number_objects is the total number of objects in the array of rods
this function prints number of "#" on each rods
 */
{
  cout<<setw(5)<<left<<"Rod"<<index<<": ";
  
 for(int i=0;i<number_objects;i++){
   cout<<"#";
 }
 cout<<setw(16-number_objects)<<right;
 cout<<"("<<fixed<<setprecision(3)<<(number_objects/double(total_number_objects)) *
100<<"%)"<<endl;
}
void draw(int list_objects[], int n_rods){
  int total_number_objects(0);// calculate total nuber of rods
  for(int i=0;i<n_rods;i++){
    total_number_objects = total_number_objects + list_objects[i]; 
  }
  for(int j=0; j< n_rods;j++){
    draw_help(j, list_objects[j], total_number_objects);
  }
}
void display_help1(int list_objects[], int n_rods)
// this function will print the smallest number of objects
{
  int min, index(0); // min is the number of objects(the smallest compare to 
others), index is the position of min
  min = list_objects[0];
  for(int i=1; i<n_rods;i++){
    if(list_objects[i]!=0)
      {
    if(list_objects[i]<min)
      {
min = list_objects[i]; 
index = i;
      }
      }
  }
 
  cout<<"Rod "<< index<<" has the smallest number of objects with "<< min<<" 
object(s)"<<endl;
}
void display_help2(int list_objects[], int n_rods)
// this function will print the largest number of objects
{
  int max, index(0); // max is the number of objects(the largest compare to 
others), index is the position of max
  max = list_objects[0];
  for(int i=1; i<n_rods;i++){
    if(list_objects[i]> max)
      {
max = list_objects[i]; 
index = i;
      }
  }
  
  cout<<"Rod "<< index<<" has the largest number of objects with "<< max<<" 
object(s)"<<endl;
}
void display_help3(int list_objects[],int n_rods){
  int total_number_rods(0);
  double average;
  for(int i=0;i<n_rods;i++){
    total_number_rods = total_number_rods + list_objects[i];
  }
  average = double( total_number_rods)/n_rods;
  cout<<"The average number of objects per rod (i.e., rods with objects) is "<< 
average <<" objects."<<endl;
}
void display_stat(int list_objects[],int n_rods)
{
  display_help1( list_objects, n_rods);
  display_help2(list_objects, n_rods);
  display_help3( list_objects, n_rods);
}
bool empty_check( int list_objects[], int n_rods)
{
  for(int i=0; i< n_rods; i++)
    {
      if(list_objects[i]!=0) // if any value in the array is not 0, then return 
false
{
  return false;
}
    }
  return true;// if for every valur in the array is 0, then return true
}
void move(int list_objects[], int n_rods, int player, int &index, int 
&n_objects_move)
{
index = move_help1(player);
  move_help2(player, index,n_rods);
    move_help3(list_objects,player, index );
    n_objects_move =  move_help4(list_objects[index],index );
}
int move_help1(int player)
{
  int index;
 cout<< "Player ("<<player<<") :Which rod would you like to play? ";
  cin >> index;
  return index;
}
void move_help2(int player,int &index, int n_rods)
{
  // this function check if user entered the valid rod number
 
  if ((index<0)|| (index> n_rods-1))
    {
      cout<<"Error 2";
      move_help1( player);
    }
}
void move_help3(int list_objects[],int player, int &index )
{
  // check whether user entered correct rod (non-empty) to play
  if(list_objects[index] <1)
    {
      cout<<"Rod "<< index<<" has zero objects. Please select a different rod. 
"<<endl;
      move_help1( player);
    }
}
int move_help4(int index_n_objects, int index)
{
  // ask user how many objects he would like to remove
  int number;// number is number user entered to remove
  cout<< "Enter number of objects to remove ("<< index_n_objects<<" or less) from 
rod "<< index <<": ";
  cin >> number;
  while(index_n_objects < number) // check if user entered the valid objects number
    {
      cout<<"Can only remove up to "<< index_n_objects<<" object(s). Please try 
again." <<endl;
      cout<< "Enter number of objects to remove ("<< index_n_objects<<" or less) 
from rod "<< index <<": ";
  cin >> number;
    }
  return number;
}
void remove(int list_objects[], int index, int n_objects_move)
{
  list_objects[index] = list_objects[index] - n_objects_move;
}
void congratulation (int player)
{
Ghias Padmakoesoema (padmakoesoema.2)
-2, Should also reject if number <= 0.
  cout<<"Congratulations! Player "<< player <<" wins"<<endl;
}
void change(int & player)
{
  if(player == 1)
    {
      player =2;
    }
  else{
    player =1;
  }
}  cout<<"Congratulations! Player "<< player <<" wins"<<endl;
}
void change(int & player)
{
  if(player == 1)
    {
      player =2;
    }
  else{
    player =1;
  }