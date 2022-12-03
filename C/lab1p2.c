# include<stdio.h>
# include<stdlib.h>


/*
 The following function will ask user the number of data sets.
*/

void dataSetNumber(int *numSets){
  printf("Enter the number of data sets(enter an integer greater than or equal to 1 to indicate the number of data sets): \n");
  scanf("%d", numSets);
 }
/*
  The following function will dynamically allocate space for
  pointer to array of float pointers to data sets (**dataSetArrayPtr)
*/
void allocateDataSetArrayPtr(float ***dataSetArrayPtr, int numSets){
 *dataSetArrayPtr = calloc(numSets,sizeof(float*));

}

/*
The following will dynamically allocate the space for pointer to size of each data set
*/
void allocateDataSetSize(int **dataSetSizesArrayPtr, int numSets){
    dataSetSizesArrayPtr = calloc(numSets, sizeof(int));
}

/* The following will allocate space for pointer to floating point data
*/
void allocateDataSetValues(float **dataSetPtr,int size){
 dataSetPtr = calloc(size, sizeof(float));
}

/* The following will set values to the pointer to floating points data*/ 
void dataSetValues(float **dataSetPtr, int size){
   int x;
   float value;
   allocateDataSetValues(dataSetPtr,size);
   /* this function will achieve the goal to allocate space for pointers to floating points array*/
   for(x =0; x < size; x++){
        scanf("%f", &value);
        if(dataSetPtr!=0){
            dataSetPtr[x] = &value;
        }
    }
}

/*
The following will ask user the size of each data set
*/
void dataSetSize(int **dataSetSizesArrayPtr ,float ***dataSetsArrayPtr,int numSets){
    int i, size;
    printf("enter the number of floating point values in each dataset followed by the floating point values themselves on the same line :\n");
    for(i = 0; i < numSets; i++){
        scanf("%d",&size);
        if(dataSetSizesArrayPtr != 0){
        dataSetSizesArrayPtr[i] = &size;
        }
        dataSetValues(dataSetsArrayPtr[i],size);
        /* Setting values in allocated storage*/
    }
}



/* The following function will print the value in dataSet 
*/

void printDataSet(float **dataSetsArrayPtr, int *dataSetSizesArrayPtr, int size){
 int y;

 printf("The following will prints the values in the data set : \n");
    for(y = 0; y < size; y++){
        printf("%.2f\t", **dataSetsArrayPtr);
    }
    printf("\n");
}

 int main(){ 


 float **dataSetsArrayPtr = NULL;
 /*pointer to array of float pointers to data sets */ 
 int *dataSetSizesArrayPtr = NULL; 
 /*pointer to array of int sizes of data sets */ 
 int numSets = 0;
  /* number of data sets */
 dataSetNumber(&numSets);

 /* ask user the number of data sets*/
 allocateDataSetArrayPtr(&dataSetsArrayPtr,numSets);
 
 /* dynamically allocate space for pointers to pointer to array of float pointers to data sets*/
 allocateDataSetSize(&dataSetSizesArrayPtr,numSets);
 
/* dynamically allocate space for pointers to pointer to array of int sizes of data sets*/ 
 dataSetSize(&dataSetSizesArrayPtr,&dataSetsArrayPtr, numSets);


 /* Set values in both the pointers*/
 /*printDataSet(dataSetsArrayPtr,dataSetSizesArrayPtr,2);*/
 return 0;
}
