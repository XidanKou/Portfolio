# include<stdio.h>
# include<stdlib.h>

/*
  The Following readData function will ask user to enter the dataset and store the value in array dataSet.
*/
void readData(float *dataSet, int dataSetSize){
    int x;
    printf("Enter the data for the data set (10 of the floating point values on a single line): \n");
    for(x =0; x < dataSetSize; x++){
        scanf("%f", &dataSet[x]);
    }
    printf("\n");
}

/*
  The following chooseOperation function will ask user which calculation they would like to do to the dataSet.
  The function will return the integer, which is the number of calculation they would like to do.
*/
int chooseOperation(){
    int choice;
    printf("\nChoose one of the following options for a calculation to perform on the data set:\n");
    printf("1) Find the minimum value.\n 2) Find the maximum value.\n 3) Calculate the sum of all the values.\n 4) Calculate the average of all the values.\n 5) Print the values in the data set.\n 6) Exit the program.\n");
    printf("Enter one of the six numbers, followed by enter: \n");
    scanf("%1d", &choice);
    printf("\n");
   return choice;
}

/*
  The fllowing minValue function will calculate the minimum value of the dataset and return the result.
*/
float minValue(const float *dataSet, int dataSetSize){
 float min = dataSet[0];
 int i;
 for (i = 1; i < dataSetSize; i++)
 {
    if (dataSet[i] < min)
    {
        min = dataSet[i];
    }
 }
  return min;
}

/*
  The fllowing maxValue function will calculate the maximum value of the dataset and return the result.
*/
float maxValue(const float *dataSet, int dataSetSize){
float max = dataSet[0];
 int i;
 for (i = 1; i < dataSetSize; i++)
 {
    if (dataSet[i] > max)
    {
        max = dataSet[i];
    }
 }
  return max;
}

/*
  The fllowing sumValue function will calculate the summation of the dataset and return the result.
*/
float sumValue(const float *dataSet, int dataSetSize){
 float sum = dataSet[0];
 int i;
 for (i = 1; i < dataSetSize; i++)
 {
    sum+=dataSet[i];
 }
  return sum;
}
/*
  The fllowing aveValue function will calculate the average value of the dataset and return the result.
*/
float aveValue(const float *dataSet, int dataSetSize){

return sumValue(dataSet,dataSetSize)/dataSetSize;
}

/*
The following printData function will print the value of the dataSet.
*/
void printData(const float *dataSet, int dataSetSize){
    int y;
    printf("The following will prints the values in the data set : \n");
    for(y = 0; y < dataSetSize; y++){
        printf("%.2f\t", dataSet[y]);
    }
    printf("\n");

}

/*
The following outputResult function will print to the user the result of the calculation.
*/
void outputResult(int choice, const float result){
    switch (choice)
    {
    case 1:
        printf("The minimum value in the data set is: %.2f \n", result);
        break;
   case 2:
        printf("The maximum value in the data set is: %.2f \n", result);
        break; 
    case 3:
        printf("The summation value in the data set is: %.2f \n", result);
        break;
    case 4:
        printf("The average value in the data set is: %.2f \n", result);
        break;
    default:
        break;
    }
}

/*
The following runOperation function will repeatedly prompt to the user which calculation they would like to do untile they choose to exit the program. 
Also calculate the result and print result to user.
*/
void runOperation(const float *dataSet, int dataSetSize){
   float result;
   const int EXIT = 6;
   int choice = chooseOperation();
   while(choice != EXIT) {
    switch (choice)
    {
    case 1:
        result = minValue(dataSet, dataSetSize);
        break;

    case 2:
        result = maxValue(dataSet, dataSetSize);
        break;

    case 3:
        result = sumValue(dataSet, dataSetSize);
        break;
    case 4:
        result = aveValue(dataSet, dataSetSize);
        break;
    case 5:
        printData(dataSet, dataSetSize);
        break;
    default:
        break;
    }
    if(choice!= 5){
      outputResult(choice,result);
    }
     choice = chooseOperation();
   }
}

int main(){

 /* declearing array and array size*/
 float dataSet[10];
 int dataSetSize = 10;

 /*read and store data into array*/
 readData(dataSet,dataSetSize);
 /*ask for calculations, run calculations, print the result*/
 runOperation(dataSet,dataSetSize);

 return 0;
}
