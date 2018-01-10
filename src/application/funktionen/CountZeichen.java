package model;

public class CountZeichen {

	public int countChar(String input, char toCount){
        int counter = 0;
        for(char c: input.toCharArray()){
            if(c==toCount)
                counter++;
        }
        return counter;
    }

}
