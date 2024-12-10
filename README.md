# Advent of Code 2024
 
These are my submissions to the 2024 Advent of Code (AoC) challenge. They are written in Java, and organized by day. Also included is my Java source code template for Advent of Code and other competitive programming contests. The solutions are usually hacked together by me during the first few minutes of each day's competition, then I go back and refine my solution to clean up my code and do optimizations. My thoughts on each day, along with my time and rankings are also provided.

# Table of Contents
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

# Summary by Day

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
