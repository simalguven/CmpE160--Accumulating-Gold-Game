import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;


public class simal_guven {
    public static int line_num;
    public static int row_num;

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input.txt");
        Scanner terrainInfo = new Scanner(file);//naming the scanner object as "terrainInfo"
        String line1 = terrainInfo.nextLine();//getting the first line that has M and N values
        row_num = Integer.parseInt(line1.split(" ")[0]);//row number
        line_num = Integer.parseInt(line1.split(" ")[1]);//line number
        int[][] matrix = new int[line_num+1][row_num+1];//matrix will be printed after each modification, and it has one more row and one more column than given-it will have line numbers and row specifying letters in it
        int[][] willBeSearched = new int[line_num][row_num];//after all modifications done on matrix, M-N matrix named willBeSearched is extracted from it
        boolean[][] booleanArray = new boolean[line_num][row_num];//a 2D boolean array equivalent to willBeSearched array-initially all values false
        for(int i=0;i<line_num;i++){//looping as many times as how many lines we have in the input
            String line2 = terrainInfo.nextLine();//getting every line one by one
            String[] filling_line = line2.split(" ");// string array filling_line is created with splitting each line in the file

            for(int j=0;j<row_num+1;j++){
                if (j==0){
                    matrix[i][j] = i;//filling the beginning of each line with that lines index (its index in matrix array)
                }else{
                    matrix[i][j]= Integer.parseInt(filling_line[j-1]);//filling each line of the matrix array with filling_line string array(equivalent to 1 index number less every time)
                }
            }
        }
        for(int i=0;i<row_num;i++){
            matrix[line_num][1+i]= (char) (97+i);//finally filling the last line of the matrix with increasing integer values of chars"1 extra line that we had in the 2D array named matrix"
        }
        print(matrix);//first state of the matrix is printed-without any modification happens
        terrainInfo.close();//close the scanner object-we have read the file
        for(int i=1;i<11;i++){//modifications on array matrix are taken totally 10 times-only counting valid inputs
            Scanner inputs = new Scanner(System.in);//opening a scanner object
            System.out.println("Add stone "+i+" / 10 to coordinate:");//prompt the user
            String modification = inputs.nextLine();//getting input from the user
            try{//trying if the modification was at first given with the lengths of 2,3, or 4
                if(modification.length()==2){//modification length is 2
                    String row = modification.split("")[0];//taking the letter in the input(1 character)
                    int line = Integer.parseInt(modification.split("")[1]);// taking the number in the input
                    int rowInt = ((int)row.charAt(0))-96;//finding which row index , the letter in the input corresponds to
                    if(line!=line_num){//line given should be smaller than line num otherwise modification will change the int value of a char
                        matrix[line][rowInt]++;//incrementing the value of the modified coordinate
                        print(matrix);//printing the modified matrix
                        System.out.println("---------------");
                    }else{//if the line number of the modification given equals the line number ask for the input again do not print anything
                        System.out.println("Not a valid step!");
                        i--;//decrease i to increment the number of loops and stabilize the prompt(i will be the same)
                    }

                }
                if(modification.length()==3){//modification length is 3//two possible input type
                    try{//a33 format
                        String row=modification.substring(0,1);//taking the letter in the input(1 character)
                        int line=Integer.parseInt(modification.substring(1));//taking the number in the input
                        int rowInt=((int)row.charAt(0))-96;//finding which row index , the letter in the input corresponds to
                        if(line!=line_num){//line given should be smaller than line num otherwise modification will change the int value of a char
                            matrix[line][rowInt]++;//incrementing the value of the modified coordinate
                            print(matrix);//printing the modified matrix
                            System.out.println("---------------");
                        }else{//if the line number of the modification given equals the line number ask for the input again do not print anything
                            System.out.println("Not a valid step!");
                            i--;//decrease i to increment the number of loops and stabilize the prompt(i will be the same)
                        }
                    }catch(NumberFormatException e){//works when integer.parsInt with last two elements (_a3) throws exception means we have aa3 format
                        String row=modification.substring(0,2);//taking the letter in the input(2 characters)
                        int line=Integer.parseInt(modification.substring(2));//taking the number in the input
                        int rowIntFirst=row.charAt(0);//first element of the string row's integer value
                        int rowIntSecond=row.charAt(1);//second element of the string row's integer value
                        int rowInt= 26*(rowIntFirst-96)+(rowIntSecond-96);//finding which row index , the letter in the input corresponds to
                        if(line!=line_num){
                            matrix[line][rowInt]++;
                            print(matrix);
                            System.out.println("---------------");
                        }else{
                            System.out.println("Not a valid step!");
                            i--;
                        }
                    }
                }
                if(modification.length()==4){//modification length is 4 //aa33 format
                    String row = modification.substring(0,2);//taking the letter in the input(2 characters)
                    int line=Integer.parseInt(modification.substring(2));//taking the number in the input
                    int rowIntFirst=row.charAt(0);//first element of the string row's integer value
                    int rowIntSecond=row.charAt(1);//second element of the string row's integer value
                    int rowInt= 26*(rowIntFirst-96)+(rowIntSecond-96);//finding which row index , the letter in the input corresponds to
                    if(line!=line_num){
                        matrix[line][rowInt]++;
                        print(matrix);
                        System.out.println("---------------");
                    }else{
                        System.out.println("Not a valid step!");
                        i--;
                    }
                }

                if(modification.length()>4||modification.length()<2){//modification input should be minimum 2 length maximum 4 length
                    System.out.println("Not a valid step!");
                    i--;
                }

            }catch(Exception e){//if any of the if statements did not work
                System.out.println("Not a valid step!");
                i--;
            }

        }


    for(int i=0;i<line_num;i++){//extracting coordinates that hold values from the array matrix and filling the willBeSearched matrix with it
        if (row_num >= 0) System.arraycopy(matrix[i], 1, willBeSearched[i], 0, row_num);
    }

        int[][] directions={{-1,0},{-1,-1},{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1}};//for inspecting the neighbors of a coordinate

        //find points that are in a lake
        for(int l=1;l<line_num-1;l++){//visiting every point on willBeSearched array
            for(int r=1;r<row_num-1;r++){//visiting every point on willBeSearched array
                boolean out=true;
                int lSearching=l;//initializing lSearching to the center coordinate
                int rSearching=r;//initializing rSearching to the center coordinate
                int value= willBeSearched[l][r];//value stays as the coordinate's value we inspect the neighbors of
                boolean[][] added = new boolean[line_num][row_num];//to prevent the movement of money to go back where it came
                Stack<int[]> lake= new Stack<>();
                added[l][r]=true;//initial position is visited
                lake.push(new int[]{l,r});//initial position is pushed to the lake stack

                while(out){//boolean out false in two cases-1st lake has been formed without flowing to a boundary point-2nd flow moved to a boundary point
                    for(int i=0; i<8;i++){//for loop for each direction
                        if(willBeSearched[lSearching+directions[i][0]][rSearching+directions[i][1]]<=value&&!added[lSearching+directions[i][0]][rSearching+directions[i][1]]){//is the inspected point met the conditions
                            lake.push(new int[] {lSearching+directions[i][0],rSearching+directions[i][1]});//pushing all neighbor values that met the conditions-smaller or equal to the center, not inspected as met the conditions beforehand
                            added[lSearching+directions[i][0]][rSearching+directions[i][1]]=true;//make the valid coordinate added, preventing not to push the same coordinate to the stack
                        }

                    }
                    for(int[] coordinate:lake){//checking every element of the stack
                        if (isBoundry(coordinate)) {
                            out=false;//one of the element is boundary, break out the loop

                        }
                    }
                    int[] coordinate = lake.pop();//getting the last added value to the lake stack
                    if(lake.isEmpty()){//if stack become empty after the preceding pop
                        booleanArray[l][r]=true;//inspected center point is a part of a lake, make it true in the main boolean 2Darray
                        out=false;//get out of the while loop, inspect the next point


                    }
                    lSearching=coordinate[0];//inspected point's line index is set to the popped elements line index
                    rSearching=coordinate[1];//inspected point's row index is set to the popped elements row index

                }

            }
        }
        //points which are in a lake are found and marked as true

        //find lakes separately with their coordinate values
        boolean[][] checked = new boolean[line_num][row_num];//to not find the same lake twice while inspecting each point//won't be recreated throughout inspecting each point that is in willBeSearched
        ArrayList<ArrayList<int[]>> lakeLists = new ArrayList<>();//will contain all lakes
        for(int l=1;l<line_num-1;l++){
            for(int r=1;r<row_num-1;r++){
                if(booleanArray[l][r]&&!checked[l][r]){//if point is a true(part of a lake) and not checked before
                    int rChecking=r;//initializing rChecking
                    int lChecking=l;//initializing lChecking
                    checked[l][r]=true;
                    boolean out = true;
                    ArrayList<int[]> lakeList = new ArrayList<>();//for every new true element encountered that is not in an already found lake(checked boolean is false) open a new lakelist
                    lakeList.add(new int[]{l,r});//lakeList contains coordinates of each point in creating that lake
                    Stack<int[]> lakeStack = new Stack<>();//for every new true element encountered that is not in an already found lake(checked boolean is false) open a new lakeStack
                    lakeStack.push(new int[]{l,r});
                    while(out){//boolean out false in one case-finished adding every point that is on the same lake to that lake's list
                        for(int i=0;i<8;i++){
                            if(booleanArray[lChecking+directions[i][0]][rChecking+directions[i][1]]&&!checked[lChecking+directions[i][0]][rChecking+directions[i][1]]){//if checked point is true and not added to a list before, or inspected for itself
                                checked[lChecking+directions[i][0]][rChecking+directions[i][1]]=true;//make it checked
                                lakeStack.push(new int[]{lChecking+directions[i][0],rChecking+directions[i][1]});//push that point to the stack
                                lakeList.add(new int[]{lChecking+directions[i][0],rChecking+directions[i][1]});//add it to the lake list

                            }
                        }
                        int[] coordinate=lakeStack.pop();//at every iteration pop an element-for changing lChecking and rChecking for the next iteration
                        if(lakeStack.isEmpty()){//if every adjacent true point has been added to the lakelist and no more nonchecked true point to add to the stack
                            out=false;//a lake have been found
                            lakeLists.add(lakeList);//added to lakeLists
                        }
                        lChecking=coordinate[0];//lChecking updated
                        rChecking=coordinate[1];//rChecking updated

                    }
                }
            }
        }
        //every lake has its own coordinates in its own list that is added to lakeLists list

        //find final score
        ArrayList<ArrayList<Integer>> valueLists = new ArrayList<>();
        ArrayList<Integer> totals = new ArrayList<>();//holding sum of the values of the coordinates each lake has
        ArrayList<Integer> numbers = new ArrayList<>();//holding how many coordinates each lake has
        for(int i=0;i<lakeLists.size();i++){//how many lake do we have
            ArrayList<Integer> valueList = new ArrayList<>();//open an arraylist for every lakeList-will hold surrounding values
            int total=0;//sum of stones in every coordinate in an investigated lake
            numbers.add(lakeLists.get(i).size());//add the current investigated lake's coordinate number, how many points create the lake
            for(int j=0;j<lakeLists.get(i).size();j++){//loop through inside current lake that is investigated
                total += willBeSearched[lakeLists.get(i).get(j)[0]][lakeLists.get(i).get(j)[1]];//update total
                for(int k=0;k<8;k++){//for using values in directions list respectively
                    if(!booleanArray[lakeLists.get(i).get(j)[0]+directions[k][0]][lakeLists.get(i).get(j)[1]+directions[k][1]]){//to get every value surround the investigated lake-should be false-shouldn't get the value on the coordinates that is on the lake
                        valueList.add(willBeSearched[lakeLists.get(i).get(j)[0]+directions[k][0]][lakeLists.get(i).get(j)[1]+directions[k][1]]);//with the current investigated coordinate add value that is in that coordinate in the willBeSearched
                    }

                }

            }
            valueLists.add(valueList);//add the list that holds values of every coordinate surrounding the current lake
            totals.add(total);//add values of all coordinate points current lake has

        }

        for(int i=0;i<valueLists.size();i++){//loop as many times as how many lakes ,how many valueList, do we have
            int min= valueLists.get(i).get(0);
            for(int element:valueLists.get(i)){
                if(element<min){
                    min=element;//find the minimum value that surrounds each lake

                }
            }
            numbers.set(i, numbers.get(i)*min);//update the numbers list as multiplying each element of it with the minimum value that surrounds the current lake-total value of that list will be subtracted from this value

        }

        double result=0;
        for (int i=0;i<numbers.size();i++){//loop as many times as how many lakes ,how many number value, do we have
            result += Math.sqrt(numbers.get(i) - totals.get(i));//for every lake(arraylist numbers and arraylist totals have same length)calculate its volume, take square root of it and increase result variable with that value
        }
        //final score is found


        //printing of the final 2D array list (that is modified 10 times and information lakes, final score are found from it) by the requested format
        int lakeNum=0;//keeps track of which lake is processed (1st?, 2nd?, ...)
        for(ArrayList<int[]> list: lakeLists){//changing the willBeSearched values -that are a lake- to a char's corresponding integer value, that value changes according to lakeNum
            lakeNum++;
            for(int i=0;i< list.size();i++){
                willBeSearched[list.get(i)[0]][list.get(i)[1]]=(char)(64+lakeNum);
            }

        }

        //printing of every line excluding last one
        for(int i=0;i<line_num;i++){
            System.out.print(" ");//blank preceding each line
            System.out.printf("%2d",i);//line index formatted

            for(int j=0;j<row_num;j++){//inside every line row values should be printed
                if(!booleanArray[i][j]){//if that coordinate is not in a lake
                    System.out.print(" ");
                    System.out.printf("%2d",willBeSearched[i][j]);//print the value itself


                }else{//if that coordinate is in a lake
                    if((willBeSearched[i][j]-65)/26==0){//in the first A-Z////calculations use the integer value that is in lake points on the willBeSearched 2d array
                        System.out.print("  ");
                        System.out.print((char)willBeSearched[i][j]);//simply print the char value of the integer


                    }else{//in AA-ZZ//calculations use the integer value that is in lake points on the willBeSearched 2d array
                        System.out.print(" ");
                        String output = String.valueOf(((char)(((willBeSearched[i][j]-65)/26)+64)));//string format of the first char that is calculated with integer value of current coordinate
                        String output1= String.valueOf((char)(willBeSearched[i][j]-((willBeSearched[i][j]-65)/26)*26));//string format of the second char that is calculated with integer value of the current coordinate
                        System.out.printf(output+output1);

                    }
                }

            }
            System.out.println();//move to the next line

            }
        //printing of every line finished excluding last one

        //printing last line -char values of every row
        System.out.print("   ");//blank preceding every line-bigger than the preceding lines
        for(int i=0;i<row_num;i++){//printing will happen for each row
            if(i>25){//require aa-zz//calculations use the row number
                System.out.print(" ");
                String output1= String.valueOf((char)((i/26)+96));//string format of the first char that is calculated with index of the row
                String output2 = String.valueOf((char)((i-((i/26)*26))+97 ));//string format of the second char that is calculated with index of the row
                System.out.print(output1+output2);

            }else{//in a-z//calculations use the row number
                System.out.print("  ");
                System.out.print((char)(i+97));//simply print the char value of the integer

            }

        }
        System.out.println();//leave a spare line
        System.out.print("Final score: ");
        System.out.printf("%.2f",result);//print the final score-result

    }

    public static boolean isBoundry(int[] coordinate){//a boolean returning method which checks if a coordinate is a boundary or not
        if(coordinate[0]==0||coordinate[0]==line_num-1||coordinate[1]==0||coordinate[1]==row_num-1){
            return true;
        }else{
            return false;
        }

    }

    public static void print(int[][] m){//void method that prints the [line_num+1][row_num+1] 2D matrix in which modifications happen on- matrix array already has line indexes and integer values of chars under each row
        for (int i=0;i<m.length;i++){//to loop through lines
            System.out.print(" ");//leaving space in front of every line
            if(m[i] == m[m.length-1]){//if it is the last line

                for (int j=0;j<m[i].length;j++){//to loop through line values
                    if(m[i][j]==0){//if encountered nonchanged and only initialized first 0 value of the last line of the 2D array matrix only print spaces

                        System.out.print("  ");
                        System.out.print("  ");
                    }else{//other values of the last line
                        if((m[i][j]-97)/26==0){//if in a-z interval

                            System.out.print((char)m[i][j]);//print char value of it and leave big space afterwards
                            if(m[i][j]==122){//only at an instance(z) printing chars will reach printing two elements(aa) only leave little space afterwards z
                                System.out.print(" ");
                            }else{
                                System.out.print("  ");
                            }

                        }else{//if in aa-zz interval
                            String output = String.valueOf(((char)(((m[i][j]-97)/26)+96)));//string format of the first char that is calculated with integer value of current coordinate
                            String output1= String.valueOf((char)(m[i][j]-((m[i][j]-97)/26)*26));//string format of the second char that is calculated with integer value of current coordinate
                            System.out.printf(output+output1);
                            System.out.print(" ");//leave a little space(aa-zz format will be successive)
                        }
                    }
            }}else{//if it is not the last line

                for (int k=0;k<m[i].length;k++){//to loop through line values

                    System.out.printf("%2d",m[i][k]);
                    System.out.print(" ");
                }
            }
            System.out.print("\n");//moving to the next line after printing a line
        }

    }

}
