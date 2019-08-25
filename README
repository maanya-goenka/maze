#maze.java

6 5
0 0
5 4
L-_|_-
|L--|_
|-_-|_
|L|L||
L__L__
then "java Maze maze.txt" should print the maze with no solution, like so:

+-----+-----+-----+-----+-----+-----+
|                 |                 |
|  S              |                 |
|                 |                 |
+-----+     +-----+     +-----+     +
|     |                 |           |
|     |                 |           |
|     |                 |           |
+     +-----+     +     +     +-----+
|                       |           |
|                       |           |
|                       |           |
+     +     +-----+     +     +-----+
|     |     |     |     |     |     |
|     |     |     |     |     |     |
|     |     |     |     |     |     |
+     +-----+     +-----+     +     +
|                 |                 |
|                 |              F  |
|                 |                 |
+-----+-----+-----+-----+-----+-----+

For example, suppose maze.txt contains:

6 5
0 0
0 4
L-_|_-
|L--|_
|-_-|_
|L|L||
L__L__

then "java Maze maze.txt" should print the maze with no solution, like so:

+-----+-----+-----+-----+-----+-----+
|                 |                 |
|  S              |                 |
|                 |                 |
+-----+     +-----+     +-----+     +
|     |                 |           |
|     |                 |           |
|     |                 |           |
+     +-----+     +     +     +-----+
|                       |           |
|                       |           |
|                       |           |
+     +     +-----+     +     +-----+
|     |     |     |     |     |     |
|     |     |     |     |     |     |
|     |     |     |     |     |     |
+     +-----+     +-----+     +     +
|                 |                 |
|  F              |                 |
|                 |                 |
+-----+-----+-----+-----+-----+-----+
On the other hand, "java Maze maze.txt --showsolution" should print the maze with the path of the solution marked, like so:

+-----+-----+-----+-----+-----+-----+
|                 |                 |
|  S     *        |                 |
|                 |                 |
+-----+     +-----+     +-----+     +
|     |                 |           |
|     |  *     *     *  |           |
|     |                 |           |
+     +-----+     +     +     +-----+
|                       |           |
|  *     *     *     *  |           |
|                       |           |
+     +     +-----+     +     +-----+
|     |     |     |     |     |     |
|  *  |     |     |     |     |     |
|     |     |     |     |     |     |
+     +-----+     +-----+     +     +
|                 |                 |
|  F              |                 |
|                 |                 |
+-----+-----+-----+-----+-----+-----+
Some mazes may have multiple solutions. For example, the maze shown above displays a solution that's two steps longer than necessary. 
This happened because my algorithm explored squares moving to the right before exploring squares moving down. Your program need not 
find the shortest path; any path that crosses no walls is acceptable.

The maze file format
For this assignment, we'll assume that our mazes are rectangular, and that they have walls along the entire outside of the maze, 
with no gaps in these outer walls. We will also specify a "start square" and an "finish square" to indicate the goal of the 
maze-solver--to get from S to F.

Maze files will have the following structure:

<Number of columns> <Number of rows>
<0-based column number of the start square> <0-based row number of the start square> 
<0-based column number of the finish square> <0-based row number of the finish square> 
<Row 0 description>
<Row 1 description>

Each row description includes a single character for each square in that row, and each character describes the left and bottom walls 
for its square. Specifically:

L means that the square has both a left and a bottom wall
| (vertical bar) means that the square has a left wall, but no bottom wall
_ (underscore) means that the square has a bottom wall, but no left wall
- (hyphen) means that the square has neither a left wall nor a bottom wall

Putting this together in a small example:

3 2     [The maze has 3 columns and 2 rows]
0 0     [The start square is at the upper left]
2 0     [The finish square is at the upper right]
L-|     [(0,0) has left and bottom walls; (1,0) has neither left nor bottom; (2,0) has just left]
L__     [(0,1) has left and bottom walls; (1,1) has just bottom; (2,1) has just bottom]

which yields this maze:

+-----+-----+-----+
|           |     |
|  S        |  F  |
|           |     |
+-----+     +     +
|                 |
|                 |
|                 |
+-----+-----+-----+

and the generate the soultion to the maze as shown in an example above.
