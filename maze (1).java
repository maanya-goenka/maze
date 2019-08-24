/**
* Maze.java
* Maanya Goenka, Katherine McFerrin
* 29.01.19
* Adapted from a program by Jeff Ondich
*/

import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Stack;
import java.util.EmptyStackException;

public class Maze {
    private ArrayList<ArrayList<MazeSquare>> rowList;

    int numberOfColumns = 0;
    int numberOfRows = 0;
    int columnOfStart = 0;
    int rowOfStart = 0;
    int columnOfFinish = 0;
    int rowOfFinish = 0;
    boolean solved = false;

    private class MazeSquare {

        private int row = 0;
        private int col = 0;
        private boolean leftWall = true;
        private boolean bottomWall = true;
        private boolean topWall = true;
        private boolean rightWall = true;
        private boolean visited = false;
        private boolean partOfTheSolution = false;

        public MazeSquare(){
            row = 0;
            col = 0;
            leftWall = false;
            bottomWall = false;
            topWall = false;
            rightWall = false;
            visited = false;
            partOfTheSolution = false;
        }

        public MazeSquare(int row, int col, boolean leftWall, boolean bottomWall, boolean topWall, boolean rightWall){
            this.row = row;
            this.col = col;
            this.leftWall = leftWall;
            this.bottomWall = bottomWall;
            this.topWall = topWall;
            this.rightWall = rightWall;
            visited = false;
            partOfTheSolution = false;
        }

        public int getRow(){
            return row;
        }

        public int getCol(){
            return col;
        }

        public boolean hasLeftWall(){
            return leftWall;
        }

        public boolean hasBottomWall(){
            return bottomWall;
        }
        public boolean hasTopWall(){
            return topWall;
        }
        public boolean hasRightWall(){
            return rightWall;
        }
        public boolean hasBeenVisited(){
            return visited;
        }
        public void setVisited(){
            visited = true;
        }

        public void setPartOfTheSolution(){
            partOfTheSolution = true;
        }
    }

    public Maze() {
        //initializing an ArrayList of ArrayLists containing mazesquare objects for each row
        rowList = new ArrayList<ArrayList<MazeSquare>>();
    }

    //takes in a text file and reads it and sets up the maze
    public void load(String fileName) {
        // Use File and Scanner to set up a Scanner for the input file
        File inputFile = new File(fileName);
        Scanner scanner = null;
        try {
            scanner = new Scanner(inputFile);
        }
        catch(FileNotFoundException e){
            System.err.println(e);
            System.exit(1);
        }
        //extracts and stores variables from each line of the text file
        int counter = 0;
        int[] arr = new int[6];
        for(int i = 0; i < 3; i++){
            String line = scanner.nextLine();
            String[] arr2 = line.split(" ");
            arr[counter]=Integer.parseInt(arr2[0]);
            arr[++counter]=Integer.parseInt(arr2[1]);
            counter++;
        }
        //sets instance variables for the Maze object
        numberOfColumns = arr[0];
        numberOfRows = arr[1];
        columnOfStart = arr[2];
        rowOfStart = arr[3];
        columnOfFinish = arr[4];
        rowOfFinish = arr[5];

        int row = 0;
        boolean top = false, bottom = false, left = false, right = false;
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            ArrayList<MazeSquare> squaresInThisRow = new ArrayList<MazeSquare>();
            for(int column = 0; column < numberOfColumns; column++){
                top = false;
                bottom = false;
                left = false;
                right = false;
                if(row > 0){
                    top=rowList.get(row-1).get(column).hasBottomWall();
                }
                else {
                    top=true;
                }
                if(column<numberOfColumns-1){
                    char nextSquare = line.charAt(column+1);
                    if(nextSquare == 'L' || nextSquare == '|'){
                        right = true;
                    }
                }
                if(column == numberOfColumns-1){
                    right = true;
                }
                if(line.charAt(column) == 'L'){
                    left = true;
                    bottom = true;
                }
                else if(line.charAt(column) == '_'){
                    bottom = true;
                    left = false;
                }
                else if(line.charAt(column) == '|'){
                    left = true;
                    bottom = false;
                }
                else if(line.charAt(column) == '-'){
                    left = false;
                    bottom = false;
                }
                MazeSquare square = new MazeSquare(row, column, left, bottom, top, right);
                squaresInThisRow.add(square);
            }
            rowList.add(squaresInThisRow);
            row++;
        }
    }

    public Stack<MazeSquare> solve(){
        Stack<MazeSquare> solution = new Stack<MazeSquare>();
        MazeSquare top = rowList.get(this.rowOfStart).get(this.columnOfStart);
        MazeSquare neighbor = new MazeSquare();
        solution.push(top);

        while(this.solved==false){
            if(solution.empty()){
                System.out.println("This maze is unsolveable");
                this.solved = true;
                return solution;
            }
            if(top.getCol()==columnOfFinish && top.getRow()==rowOfFinish){
                System.out.println("This maze has been solved");
                this.solved = true;
                return solution;
            }
            top = solution.peek();
            neighbor = getNeighborRight(top);
            if(neighbor.equals(top)){
                neighbor = getNeighborDown(top);
                if(neighbor.equals(top)){
                    neighbor = getNeighborLeft(top);
                    if(neighbor.equals(top)){
                        neighbor = getNeighborUp(top);
                        if(neighbor.equals(top) == false){
                            top.setPartOfTheSolution();
                            top.setVisited();
                            solution.push(neighbor);
                            top = neighbor;
                            top.setVisited();
                        }
                        else{
                            top.setVisited();
                            solution.pop();
                        }
                    }
                    else{
                        top.setPartOfTheSolution();
                        top.setVisited();
                        solution.push(neighbor);
                        top = neighbor;
                        top.setVisited();

                    }
                }
                else{
                    top.setPartOfTheSolution();
                    top.setVisited();
                    solution.push(neighbor);
                    top = neighbor;
                    top.setVisited();
                }
            }
            else{
                top.setPartOfTheSolution();
                top.setVisited();
                solution.push(neighbor);
                top = neighbor;
                top.setVisited();
            }
        }
        return solution;
    }

    //if neighbor hasn't been visited, the top MazeSquare is marked as visited, partOfTheSolution
    //and the neighbor square is pushed onto the stack of possible solution MazeSquares
    public MazeSquare getNeighborRight(MazeSquare currentSquare){
        MazeSquare neighbor = currentSquare;
        if(currentSquare.getCol() != (this.numberOfColumns-1) && currentSquare.hasRightWall() == false){
            if(rowList.get(currentSquare.getRow()).get(currentSquare.getCol()+1).hasBeenVisited()== false){
                neighbor = rowList.get(currentSquare.getRow()).get(currentSquare.getCol()+1);
                return neighbor;
            }
        }
        return neighbor;
    }

    public MazeSquare getNeighborDown(MazeSquare currentSquare){
        MazeSquare neighbor = currentSquare;
        if(currentSquare.getRow() != (this.numberOfRows-1) && currentSquare.hasBottomWall() == false){
            if(rowList.get(currentSquare.getRow()+1).get(currentSquare.getCol()).hasBeenVisited() == false){
                neighbor = rowList.get(currentSquare.getRow()+1).get(currentSquare.getCol());
                return neighbor;
            }
        }
        return neighbor;
    }

    public MazeSquare getNeighborLeft(MazeSquare currentSquare){
        MazeSquare neighbor = currentSquare;
        if(currentSquare.getCol() != 0 && currentSquare.hasLeftWall() == false ) {

            if(rowList.get(currentSquare.getRow()).get(currentSquare.getCol()-1).hasBeenVisited() == false){
                neighbor = rowList.get(currentSquare.getRow()).get(currentSquare.getCol()-1);
                return neighbor;
            }
        }
        return neighbor;
    }

    public MazeSquare getNeighborUp(MazeSquare currentSquare){
        MazeSquare neighbor = currentSquare;
        if(currentSquare.getRow() != 0 && currentSquare.hasTopWall() == false){

            if(rowList.get(currentSquare.getRow()-1).get(currentSquare.getCol()).hasBeenVisited() == false){
                neighbor = rowList.get(currentSquare.getRow()-1).get(currentSquare.getCol());
                return neighbor;
            }
        }
        return neighbor;
    }


    public void print() {
        ArrayList<MazeSquare> currentRow;
        MazeSquare currentSquare = new MazeSquare();
        //prints the maze by row
        for(int row = 0; row < numberOfRows; row++){
            currentRow = rowList.get(row);
            //prints the top line of every row
            for(int col = 0; col < numberOfColumns; col++){
                currentSquare = currentRow.get(col);
                System.out.print("+");
                if(currentSquare.hasTopWall()){
                    System.out.print("-----");
                }
                else {
                    System.out.print("     ");
                }
            }
            System.out.println("+");//finishes the first top line of every row
            for(int i = 0; i<3; i++){
                //loops through and prints the inner contents of each square
                for(int col = 0; col<numberOfColumns ;col++){
                    currentSquare = currentRow.get(col);
                    if(col == columnOfStart && row == rowOfStart && i==1){
                        //start square
                        if(currentSquare.hasLeftWall()){
                            System.out.print("|  S  ");
                        }
                        else {
                            System.out.print("   S  ");
                        }
                    }
                    else if(col == columnOfFinish && row == rowOfFinish && i==1){
                        //finish square
                        if(currentSquare.hasLeftWall()){
                            System.out.print("|  F  ");
                        }
                        else {
                            System.out.print("   F  ");
                        }
                    }
                    else {
                        if(currentSquare.hasLeftWall() && this.solved == true && i==1 && currentSquare.partOfTheSolution == true){
                            System.out.print("|  *  ");
                        }
                        else if(currentSquare.hasLeftWall() && this.solved == false){
                            System.out.print("|     ");
                        }
                        else if( currentSquare.hasLeftWall()){
                            System.out.print("|     ");
                        }
                        else {
                            if(this.solved == true && i==1 && currentSquare.partOfTheSolution == true){
                                System.out.print("   *  ");
                            }
                            else {
                                System.out.print("      ");
                            }
                        }
                    }
                }
                System.out.println("|");
            }
        }
        //prints the last line of the maze
        for(int col = 0; col<numberOfColumns; col++){
            System.out.print("+-----");
        }
        System.out.println("+");
    }

    //main method
    public static void main(String[] args) {
        if (args.length == 0 || args.length > 2) {
            System.err.println("Usage: java Maze mazeFile [--showsolution]");
            System.exit(1);
        }

        Maze maze = new Maze();

        if(args.length == 2 && args[1].equals("--showsolution")){
            maze.load(args[0]);
            maze.solve();
            maze.print();
        }
        else{
            maze.load(args[0]);
            maze.print();
        }
        }
}
