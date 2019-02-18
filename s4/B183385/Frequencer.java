package s4.B183385;
import java.lang.*;
import s4.specification.*;

/*package s4.specification;
public interface FrequencerInterface {     // This interface provides the design for frequency counter.
    void setTarget(byte  target[]); // set the data to search.
    void setSpace(byte  space[]);  // set the data to be searched target from.
    int frequency(); //It return -1, when TARGET is not set or TARGET's length is zero
                    //Otherwise, it return 0, when SPACE is not set or SPACE's length is zero
                    //Otherwise, get the frequency of TAGET in SPACE
    int subByteFrequency(int start, int end);
    // get the frequency of subByte of taget, i.e target[start], taget[start+1], ... , target[end-1].
    // For the incorrect value of START or END, the behavior is undefined.
}
*/

public class Frequencer implements FrequencerInterface{
    // Code to start with: This code is not working, but good start point to work.
    byte [] myTarget;
    byte [] mySpace;
    boolean targetReady = false;
    boolean spaceReady = false;
    int []  suffixArray;

    // The variable, "suffixArray" is the sorted array of all suffixes of mySpace.
    // Each suffix is expressed by a integer, which is the starting position in mySpace.
    // The following is the code to print the variable
    private void printSuffixArray() {
	if(spaceReady) {
	    for(int i=0; i< mySpace.length; i++) {
		int s = suffixArray[i];
		for(int j=s;j<mySpace.length;j++) {
		    System.out.write(mySpace[j]);
		}
		System.out.write('\n');
	    }
	}
    }
    
    private int suffixCompare(int i, int j) {
		// comparing two suffixes by dictionary order.
		// i and j denoetes suffix_i, and suffix_j
		// if suffix_i > suffix_j, it returns 1
		// if suffix_i < suffix_j, it returns -1
		// if suffix_i = suffix_j, it returns 0;
		// It is not implemented yet,
		// It should be used to create suffix array.
		// Example of dictionary order
		// "i" < "o" : compare by code
		// "Hi" < "Ho" ; if head is same, compare the next element
		// "Ho" < "Ho " ; if the prefix is identical, longer string is big
		for(;i < mySpace.length && j < mySpace.length;i++,j++){
	      if(mySpace[i] > mySpace[j]){
	        return 1;
	      }
	      else if(mySpace[i] < mySpace[j]){
	        return -1;
	      }
	    }
	    if(j>i){
			return 1;
		}else if(j<i){ return -1;
		}
		return 0;
    }

	//Changed to MergeSort for O(nlog(n)
    public void MergeSort(int[] array, int low, int high){
    	if(low < high){
    		int middle = (low + high)/2;
    		MergeSort(array, low ,middle);
    		MergeSort(array, middle+1, high);
	        merge(array, low, middle, high);
    	}
    }

    public void merge(int[] array, int low, int middle, int high){
	        int[] h = new int[array.length];
	        for (int i = low; i <= high; i++){
	            h[i] = array[i];
	        }
	        int hLeft = low;
	        int hRight = middle + 1;
			int current = low;
			
	        while (hLeft <= middle && hRight <= high){
	          int i=0;
	          if(suffixCompare(h[hLeft]+i,h[hRight]+i)==1){
	            array[current] = h[hRight];
	            hRight ++;
	          }
	          else {
	              array[current] = h[hLeft];
	              hLeft ++;
	          }
	          i++;
	          current ++;
	        }
	        int remaining = middle - hLeft;
	        for (int i = 0; i <= remaining; i++){
	            array[current + i] = h[hLeft + i];
	        }
	    }

    public void setSpace(byte []space) { 
		mySpace = space; 
		if(mySpace.length>0) spaceReady = true; 
		suffixArray = new int[space.length];
		// put all suffixes  in suffixArray. Each suffix is expressed by one integer.
		for(int i = 0; i< space.length; i++) {
	    	suffixArray[i] = i;
		}
		// Sorting is not implmented yet.
		/* Example from "Hi Ho Hi Ho"
		0: Hi Ho
		1: Ho
		2: Ho Hi Ho
		3:Hi Ho
		4:Hi Ho Hi Ho
		5:Ho
		6:Ho Hi Ho
		7:i Ho
		8:i Ho Hi Ho
		9:o
		A:o Hi Ho
		*/
		//
		// int temp;
		// for (int i = 0; i < space.length; i++) {
		// 	for (int j = space.length - 1; j > i; j--) {
		// 		int k = suffixCompare(j, j - 1);
		// 		if (k == -1) {
		// 			temp = suffixArray[j];
		// 			suffixArray[j] = suffixArray[j - 1];
		// 			suffixArray[j - 1] = temp;
		// 		}
		// 	}
		// }
		// ****  Please write code here... ***
		MergeSort(suffixArray,0,suffixArray.length-1);
    }

    private int targetCompare(int i, int j, int end) {
		// comparing suffix_i and target_start_end by dictonary order with limitation oflength;
		// if the beginning of suffix_i matches target_i_end, and suffix is longer than target it returns 0;
		// if suffix_i > target_start_end it return 1;
		// if suffix_i < target_start_end it return -1
		// It is not implemented yet.
		// It should be used to search the apropriate index of some suffix.
		// Example of search
		// suffix target
		// "o" > "i"
		// "o" < "z"
		// "o" = "o"
		// "o" < "oo"
		// "Ho" > "Hi"
		// "Ho" < "Hz"
		// "Ho" = "Ho"
		// "Ho" < "Ho " : "Ho " is not in the head of suffix "Ho"
		// "Ho" = "H" : "H" is in the head of suffix "Ho"

		int n = suffixArray[i];
    	for(; j<end; j++, n++){
	    	if(mySpace[n] > myTarget[j]){
	    		return 1;
	    	}else if(mySpace[n] < myTarget[j]){
	    		return -1;
	    	}else if(mySpace.length-n == 1 && end-j != 1){
	    		return -1;
	    	}
	    }
	    return 0;
    }

    private int subByteStartIndex(int start, int end) {
		// It returns the index of the first suffix which is equal or greater than subBytes;
		// not implemented yet;
		// For "Ho", it will return 5 for "Hi Ho Hi Ho".
		// For "Ho ", it will return 6 for "Hi Ho Hi Ho".
		// for (int i = 0; i < suffixArray.length; i++) {
		// 	//System.out.println(i + ": " + targetCompare(i, start, end));
		// 	if (targetCompare(i, start, end) == 0) {
		// 		return i;
		// 	}
		// }
    	for(int i=0; i<suffixArray.length; i++){
    		if(targetCompare(i,start,end) == 0){
    			return i;
    		}
    	}
		return suffixArray.length; // This line should be modified.
    }

    private int subByteEndIndex(int start, int end) {
	// It returns the next index of the first suffix which is greater than subBytes;
	// not implemented yet
	// For "Ho", it will return 7  for "Hi Ho Hi Ho".
	// For "Ho ", it will return 7 for "Hi Ho Hi Ho".
	//
	// ****  Please write code here... ***
	//	
    	
    	for(int i = subByteStartIndex(start,end); i < suffixArray.length; i++){
    		if(targetCompare(i,start,end) == 1){
    			return i;
    		}
    	}
		return suffixArray.length; // This line should be modified.
    }

    public int subByteFrequency(int start, int end) {
	 //This method be work as follows, but
		int spaceLength = mySpace.length;
		int count = 0;
		for(int offset = 0; offset< spaceLength - (end - start); offset++) {
		    boolean abort = false;
		    for(int i = 0; i< (end - start); i++) {
				if(myTarget[start+i] != mySpace[offset+i]) { abort = true; break; }
		    }
		    if(abort == false) { count++; }
		}
		
		int first = subByteStartIndex(start, end);
		int last1 = subByteEndIndex(start, end);
		return last1 - first;
    }

    public void setTarget(byte [] target) { 
		myTarget = target; 
		if(myTarget.length>0) 
			targetReady = true; 
    }

    public int frequency() {
		if(targetReady == false) return -1;
		if(spaceReady == false) return 0;
		return subByteFrequency(0, myTarget.length);
    }

    public static void main(String[] args) {
		Frequencer frequencerObject;
	try {
	    frequencerObject = new Frequencer();
	    frequencerObject.setSpace("Hi Ho Hi Ho".getBytes());
	    frequencerObject.printSuffixArray(); // you may use this line for DEBUG
	    frequencerObject.setTarget("H".getBytes());
	    // ****  Please write code to check subByteStartIndex, and subByteEndIndex
	    int end = frequencerObject.myTarget.length;
	    System.out.println("----- targetCompare's test ----");
	    for(int i = 0; i<11; i++){
	    	System.out.println(+i +" : " +frequencerObject.targetCompare(i,0,end));
	    }
	    System.out.println("----- subByteStartIndex's test ----");
	    System.out.println(frequencerObject.subByteStartIndex(0,end));
	    System.out.println("----- subByteendIndex's test ----");
	    System.out.println(frequencerObject.subByteEndIndex(0,end));
	    System.out.println("----- frequency's test ----");
	    int result = frequencerObject.frequency();
	    System.out.println("Freq = "+ result+" ");
	    if(4 == result) { System.out.println("OK"); } else {System.out.println("WRONG"); }
	}
	catch(Exception e) {
	    System.out.println("STOP");
		}
    }
}