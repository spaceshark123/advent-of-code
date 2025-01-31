# Advent of Code
 
These are my submissions to all the Advent of Code (AoC) challenges that I have participated in thus far (starting in 2024). They are written in Java, and organized by day. Also included is my Java source code template for Advent of Code and other competitive programming contests. The solutions are usually hacked together by me during the first few minutes of each day's competition, then I go back and refine my solution to clean up my code and do optimizations. My thoughts on each day, along with my time and rankings are also provided.

# Table of Contents

### [2024](https://adventofcode.com/2024)
- [Day 01](#day-01) | [Website](https://adventofcode.com/2024/day/1)
- [Day 02](#day-02) | [Website](https://adventofcode.com/2024/day/2)
- [Day 03](#day-03) | [Website](https://adventofcode.com/2024/day/3)
- [Day 04](#day-04) | [Website](https://adventofcode.com/2024/day/4)
- [Day 05](#day-05) | [Website](https://adventofcode.com/2024/day/5)
- [Day 06](#day-06) | [Website](https://adventofcode.com/2024/day/6)
- [Day 07](#day-07) | [Website](https://adventofcode.com/2024/day/7)
- [Day 08](#day-08) | [Website](https://adventofcode.com/2024/day/8)
- [Day 09](#day-09) | [Website](https://adventofcode.com/2024/day/9)
- [Day 10](#day-10) | [Website](https://adventofcode.com/2024/day/10)
- [Day 11](#day-11) | [Website](https://adventofcode.com/2024/day/11)
- [Day 12](#day-12) | [Website](https://adventofcode.com/2024/day/12)
- [Day 13](#day-13) | [Website](https://adventofcode.com/2024/day/13)
- [Day 14](#day-14) | [Website](https://adventofcode.com/2024/day/14)
- [Day 15](#day-15) | [Website](https://adventofcode.com/2024/day/15)
- [Day 16](#day-16) | [Website](https://adventofcode.com/2024/day/16)
- [Day 17](#day-17) | [Website](https://adventofcode.com/2024/day/17)
- [Day 18](#day-18) | [Website](https://adventofcode.com/2024/day/18)
- [Day 19](#day-19) | [Website](https://adventofcode.com/2024/day/19)
- [Day 20](#day-20) | [Website](https://adventofcode.com/2024/day/20)
- [Day 21](#day-21) | [Website](https://adventofcode.com/2024/day/21)
- [Day 22](#day-22) | [Website](https://adventofcode.com/2024/day/22)
- [Day 23](#day-23) | [Website](https://adventofcode.com/2024/day/23)
- [Day 24](#day-24) | [Website](https://adventofcode.com/2024/day/24)

# Summary by Day

## 2024

### Day 01

Really easy problem, but had difficulties taking input, as it was my first advent competition.

**Part 1**: Time `00:04:19`, Rank `1538`      
**Part 2**: Time `00:07:26`, Rank `1735`

### Day 02

Should have been a simple problem, but I had trouble covering all the edge cases in my implementation. I also wasted a lot of time trying to create an optimized solution when brute force was sufficient. 

**Part 1**: Time `00:14:07`, Rank `4651`      
**Part 2**: Time `00:25:07`, Rank `3786`

### Day 03

The problem was pretty interesting, and I thought I came up with a decent solution that manually parsed through the input string, but found out after that you could just use regex for a super simple solution, which I didn't know at the time.

**Part 1**: Time `00:17:27`, Rank `7329`      
**Part 2**: Time `00:26:42`, Rank `5265`

### Day 04

Crossword was a fun problem, my original solution worked, but was super ugly, with a lot of if else statements, so I went back and extracted the information into an indexable array. This ended up being a recurring technique in future days.

**Part 1**: Time `00:08:08`, Rank `1185`      
**Part 2**: Time `00:37:28`, Rank `4568`

### Day 05

Problem seemed contrived to me, and taking input was a big challenge, actually. I spent the most time on the valid check logic, which I solved using brute force (are you sensing a theme?) I found a massive optimization after I had completed the challenge, by simply breaking from the loop if I found a solution.

**Part 1**: Time `00:17:08`, Rank `3271`      
**Part 2**: Time `00:24:13`, Rank `2190`

### Day 06

Part 1 was fun and used a pretty simple simulation of the guard, but part 2 stumped me. I spent way too long trying to create a "smart" solution and kept running into weird edge cases, and just used brute force, which I later optimized by only considering locations along the original path from part 1. Poor performance on my end.

**Part 1**: Time `00:10:34`, Rank `1274`      
**Part 2**: Time `01:37:12`, Rank `6222`

### Day 07

Problem was really easy? Especially when compared to yesterday, I was expecting a similar difficulty but today marked my first sub-1000 rank placement, almost sub-500! Very simple solution and gave me the chance to use bitwise operations.

**Part 1**: Time `00:06:40`, Rank `738`      
**Part 2**: Time `00:08:16`, Rank `505`

### Day 08

Another easy problem, just a simple loop over all pairs of antennae with the same frequency and constructing all multiples of the difference between them. This marked my first ever sub-500 rank! I spent a while trying to optimize the solution to calculate the bounds, or at least prune them, and I did, improving the runtime. I'm very happy with today's results.

**Part 1**: Time `00:09:47`, Rank `673`      
**Part 2**: Time `00:13:12`, Rank `464`

### Day 09

Pretty cool problem although it took a while to understand what it wanted. Finished part 1 pretty quick, but could have done better (I messed up by using int instead of long, causing overflow). This is the second time I've done this, so I should have known better. Part 2 was pretty simple as well, but took longer to code. Surprisingly, a time over 30 minutes still put me at sub-1000! I guess this means the LLM cheaters are starting to struggle with the problems, or the questions are just getting harder. I could have gotten another sub-500 rank, if not for the int-long issue, but decent performance.

**Part 1**: Time `00:10:49`, Rank `503`      
**Part 2**: Time `00:33:19`, Rank `813`

### Day 10

Interesting problem, used some simple BFS (breadth first search) to solve part 1 using a hashset to filter out duplicates, then realized part 2 just involved allowing duplicates, so changing the datatype from hashset to arraylist was all that was needed. I am annoyed that I messed up my input reading again and didn't realize, which wasted a lot of extra time when I was trying to find the problem in my algorithm. Maybe I should start checking if I read the input correctly before solving the problem. Pretty bad rank.

**Part 1**: Time `00:19:33`, Rank `2934`      
**Part 2**: Time `00:20:52`, Rank `2255`

### Day 11

At first, it seemed like a simple simulation problem with a slightly convoluted description, with a few bugs along the way involving the overflow of ints (I know, I know, I should be defaulting to longs by now). But the real trouble started with part 2, which looked deceptively simple: increase the blink count from 25 to 75. But due to the exponential growth of the values, things quickly got out of hand for my solution, causing many frustrations as I battled crawling runtimes and heap/memory overflow errors. I finally looked on the reddit for a hint, which was to use dynamic programming. This ended up drastically simplifying the problem, especially after the memoization was implemented. What I failed to realize was that there was many duplicate stones being solved over and over again, so memoization was effective. Many people also used counters to keep track of the lists instead of storing the actual list to reduce redundancy, which was smart. I think today marked the starting point where the problems start needing actual optimization and critical thinking to solve, as opposed to brute force. Insightful day.

**Part 1**: Time `00:14:56`, Rank `4060`      
**Part 2**: Time `01:17:24`, Rank `5814`

### Day 12

Wow. Just wow. I did not expect day 12 to hit me so hard. Part 1 was easy enough, using a simple flood fill algorithm to find the area of each connected group and its perimeter and I got around 500th rank on that one. But part 2 asking for the number of sides stumped me with its variety of cases to deal with and just how to approach the problem in general. Shapes could be convex, concave (meaning multiple normal vectors in the same coordinate), have other shapes inside of them, be diagonal from each other, and more. I spent multiple hours trying to solve it using different approaches, as I got increasingly desperate, wanting to quit many times. Finally, I realized that I could just use flood fill on the normals and count the number of distinct groups to get the number of sides. After 2 and a half hours, I'm pleasantly surprised I still got 5000th place! That was a nasty problem.

**Part 1**: Time `00:08:33`, Rank `554`      
**Part 2**: Time `02:29:35`, Rank `5125`

### Day 13

Day 13 was hard, but a fun problem! It involved optimization, so I instantly thought of dynamic programming and a very similar problem I had seen before, first implementing a recursive solution, then using memoization to speed it up. This passed part 1 with O(n^2) runtime. However, the challenge introduced by part 2 was too big for even dp to solve in reasonable time. After being stumped for a while, I realized that this was just a system of equations (specifically diophantine equations with nonnegative solutions), which I could solve using linear algebra and Cramer's rule in O(1) constant runtime. It was fun and unexpected to have to use a direct math solution instead of a cs algorithm. I spent a long time figuring out what do to in the case of infinite solutions to the system, in which case I actually had to minimize the tokens, but later found out that apparently none of the inputs in the problem actually covered this case, so I could have just skipped it. That would have been bad for completion's sake though, so I'm glad I did the full problem.

**Part 1**: Time `00:27:22`, Rank `2962`      
**Part 2**: Time `01:48:54`, Rank `5111`

### Day 14

Part 1 was trivial, with a simple simulation through 100 timesteps after creating a custom robot class and step function. Part 2, though, was "fun" (I say with quotations because the problem itself was actually very vaguely defined). We had to find the timestep in which a "tree" appeared in the grid, with no further explanation. Since I had no way of knowing what the author meant by a tree, I started manually going through every single timestep. Then, I guessed that the tree would probably have a lot of robots next to each other, so I started selecting the timesteps with above a certain threshold of consecutive robots, which actually worked and I found the tree! Pretty cool to see.

**Part 1**: Time `00:14:53`, Rank `1282`      
**Part 2**: Time `00:59:02`, Rank `2653`

### Day 15

I wasn't feeling well, so I didn't try to compete in the day 15 leaderboards, and looked at the problem the next day. The problem description seemed like a very straightforward simulation problem, and part 1 was quick, but part 2 spiced things up with the fact that pushing a single block could push multiple other blocks, possibly starting a chain reaction. I had to propagate the push testing logic throughout the blocks, which needed some pretty involved steps. But good problem overall.

**Part 1**: Time `23:48:24`, Rank `30116`      
**Part 2**: Time `>24h`, Rank `21598`

### Day 16

a maze solving problem! Very interesting, but I feel like this type of problem has been asked a lot, so I was expected a lower rank than normal, with people just copy-pasting from their previous code. I solved using Djikstra's algorithm to get the minimum score for part 1 (the maze was like a weighted graph between nodes defined by x,y,dir), and used some backtracking to reconstruct the paths for part 2. I'm noticing a lot of 2D grid problems lately, let's see if the trend continues.

**Part 1**: Time `00:21:05`, Rank `1391`      
**Part 2**: Time `00:36:19`, Rank `929`

### Day 17

Absolutely gory problem. Part 1 started innocently enough, creating a simple virtual machine with instructions reminiscent of assembly or machine code. But part 2 was a doozy. No obvious solutions came to my head except brute force, which I tried unsuccessfully for a few hours, and even parallelized using java streams. While doing this, I analyzed the program and decompiled it into its bare logic, and figured out that the program uses 1 octal digit of A (3 bits) for every digit of output. So I created a reverse engineer function that figured out each digit of A for the output digits in reverse. But the part that stumped me was the fact that every single output digit was being reconstructed EXCEPT for the first digit. I spent way too long trying to find the error, and I couldn't. Finally, I just decided to use my brute-force technique, but around the neighborhood of the answer that I got from the almost-correct reverse engineered solution. This actually worked, and gave me the answer.

**Part 1**: Time `00:17:43`, Rank `844`      
**Part 2**: Time `02:53:46`, Rank `2447`

### Day 18

Very easy day today and nice change of pace! I just used a simple BFS/Dijkstra search to find the length of the shortest path for part 1, and a floodfill to find the first obstacle that makes a solution impossible. I got 300 rank, which is a new best! Since I was starting to see 2D pathfinding pop up a lot, I looked into the A* algorithm, which combines the distance with a heuristic to prioritize looking in the direction towards the target. However, this didn't produce the optimal solution (2 cells longer than the best path), so I had to stick with the original solution.

**Part 1**: Time `00:07:20`, Rank `400`      
**Part 2**: Time `00:10:09`, Rank `300`

### Day 19

Pretty simple solution, using dynamic programming to get the number of ways to create each pattern using the towels, counting the nonzero results for part 1, and summing the results for part 2. I tried to use more of a functional programming approach for today, making extensive use of my favorite Java feature (Java streams) to streamline the pipeline process.

**Part 1**: Time `00:27:04`, Rank `3334`      
**Part 2**: Time `00:33:04`, Rank `2571`

### Day 20

Interesting problem! I finished part 1 really fast using a brute force approach that did some heavy pruning before the actual computations, which got me 165th rank (new PB! and very close to top 100 as well!). But part 2 had me stumped. I fixated on trying to compute the possible wall pairs and computing the path between them that could form a valid shortcut, but that logic was not only convoluted but also didnt take into account the fact that you could cheat over empty cells as well. I finally figured out that I could just think of it as pairs of empty cells with a manhattan distance less than 20 and directly compute the difference for each. The rank disparity between my part 1 and part 2 performance has been more apparent recently.

**Part 1**: Time `00:11:14`, Rank `165`      
**Part 2**: Time `01:56:45`, Rank `2910`

### Day 21

I finally reached my limit. Today was the first day that I just couldn't solve, even after looking at the solutions and failing to understand them. Part 1 was straightforward, then part 2 involved scaling up part 1 from 2 to 25 robots, and with the exponential time complexity, this was simply infeasible. The only breakthrough I made was using memoization to store the optimal order for each key sub-sequence, which reduced the number strings to 1, but the issue was that the strings were just getting too long for Java or my PC to handle. I even tried running it with 10GB of allocated RAM, but I could only get up to ~18 robots before a memory overflow occurred. I later found out that the length of the strings would have ran into the billions, and would have needed over 100 gigs of RAM to even store. Everyone seemed to struggle with this problem.

**Part 1**: Time `01:22:51`, Rank `828`      
**Part 2**: Time `DNF`, Rank `DNF`

### Day 22

Really simple problem! Part 1 was a simple simulation problem, which I later changed to use bitwise shifts instead of divisions/multiplications. Part 2 involved a simple brute force after calculating all unique sequences of 4 diffs, which I parallelized using Java Streams once again. got sub-500 rank for part 1.

**Part 1**: Time `00:04:59`, Rank `383`      
**Part 2**: Time `00:25:31`, Rank `596`

### Day 23

We finally got an actual graph problem! Luckily, I had already prepared a GraphNode utility class beforehand, so it was pretty easy to set up the graph. Part 1 involved a simple 3-cycle check while part 2 was more involved, and I had to use BFS to construct all fully-connected groups within the entire graph, and then sort and output the biggest one. Pretty good rank! Tonight will probably be my last night competing for leaderboards, as I'll be leaving for vacation tomorrow.

**Part 1**: Time `00:04:56`, Rank `384`      
**Part 2**: Time `00:21:24`, Rank `822`

### Day 24

I came back to AoC after a break, and wow, what a problem to come back to. Today's problem involved circuits and logic gates, which was an interesting hardware-focused change of pace. Part 1 was simple enough, with an easy brute force solution since I didn't want to implement topological sort. But part 2 was crazy complex. Having to find the swapped gates in a full binary adder was not trivial at all. I tried to reverse engineer the incorrect gates by detecting incorrect bits: didn't work. I tried brute forcing all possible 4 pairs with pruning: didn't work. I tried programatically generating a graphviz file to visualize the logic gates and maybe try to manually spot the errors, didn't work. In the end, I couldn't manage to solve part 2.

**Part 1**: Time `>24h`, Rank `25391`      
**Part 2**: Time `DNF`, Rank `DNF`