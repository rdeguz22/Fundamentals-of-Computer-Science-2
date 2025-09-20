# SUMMARY

Please read and follow the directions. Please put your answers after
the italicized instructions.

## Questions within instructions

_These two questions are worth a total of 4 points._

### Milestone 3

_Should CachingMinimaxPlayer give the same results as MinimaxPlayer or better ones? Explain._

CachingMinimaxPlayer should give the same results as MinimaxPlayer in terms of move quality and outcome
because it uses the same minimax algorithm to decide moves. The difference is CachingMinimaxPlayer uses a
transportation table to avoid recalculating the same board states, making it faster.

_What is the timing output for the specified pairs of simulators on a size-3 board?_

For Minimax and Random:
Time used by Caching Minimax (White): 64,557 μs 64 ms 0 s
Time used by Random (Black): 27 μs 0 ms 0 s

For RandomMax and Random:
Time used by Caching Minimax (White): 60,977 μs 60 ms 0 s
Time used by Random (Black): 27 μs 0 ms 0 s

For Caching Minimax and Random:
Time used by Caching Minimax (White): 24,840 μs 24 ms 0 s
Time used by Random (Black): 27 μs 0 ms 0 s

### Milestone 4

_What is the timing output when you run the following pairs of simulators on a size-5 board?_

For Minimax and Random:
Time used by Minimax (White): 34,871,401 μs 34,871 ms 34 s
Time used by Random (Black): 93 μs 0 ms 0 s

For RandomMax and Random:
Time used by RandomMax (White): 35,717,257 μs 35,717 ms 35 s
Time used by Random (Black): 41 μs 0 ms 0 s

For CachingMinimax and Random:
Time used by Caching Minimax (White): 9,048,991 μs 9,048 ms 9 s
Time used by Random (Black): 68 μs 0 ms 0 s

For AlphaBeta and Random:
Time used by Alpha-Beta (White): 289,137 μs 289 ms 0 s
Time used by Random (Black): 50 μs 0 ms 0 s

## Logistics

### Where can we find your work on github?

_Give the usernames of all teammates and the HTTPS URL of the repository page so we can view
it in a browser._ [1 point]

Usernames: rdeguz22 & dougconrad
URL: https://github.com/Fundies-2-Oakland/homework-11-islands-of-hex-game-trees-rico-d.git

### Did you successfully implement everything that was requested? [1 point]

_Answer "Yes", or state here which parts did not work or which tests did not
pass._

Yes I implemented everything to the best of my ability.

### How long do you estimate the assignment took? [1 point]

_Estimate the number of hours or minutes. Rather than giving a range, if you are unsure,
give the average of the range so we can enter the estimate in a spreadsheet._

This assignment took around 3 hours.

### Who did you work with and how? [1 point]

_Discussing the assignment with people not on your team is fine as long as you
don't share code. Please state whether you attended any office hours. We especially
like hearing about helpful TAs._

Pair Programming with Ricardo de Guzman and Douglas Conrad. I did not attend office hours.

### What was your AI usage? [1 point]

_State explicitly (yes or no) whether you used AI. If so, say which tools you
used and what your prompts were. Your assignment will not be graded if you do
not answer this question._

I only used AI to gain a better understanding on the Minimax algorithm and alpha-beta pruning.
I used the prompt "Explain what the minimax algorithm and alpha-beta pruning are in great detail
and give examples." It helped me gain a deeper understanding and helped in completing
this assignment.

### Did you attend the class sessions where this assignment was discussed?

_You will not be penalized for not attending class. We would just like to
know how helpful attendance was for this assignment._ [1 point]

Ricardo attended all class sessions but the April 3rd class due to sickness. Douglas attended all class sessions.

### What resources did you use?

_Please give specific URLs/questions (not just "Stack Overflow" or "ChatGPT") and
state which ones were particularly helpful._ [1 point]

We used class slides, more specifically the April 3rd Homework 11 Prep and the ones on trees. They were
vey helpful in gaining an understanding on how to complete this assignment.

## Reflections

_Give one or more paragraphs reflecting on your experience with the
assignment, including answers to all of these questions:_

* _Were the tests helpful? If so, how?_
* _What was the most difficult part of the assignment?_
* _What was the most rewarding part of the assignment?_
* _What did you learn doing the assignment?_
* _What were your favorite assignments this semester? What were your least favorites?_

_Constructive and actionable suggestions for improving assignments, office hours,
and lecture are always welcome. If there was a TA who was especially helpful,
say so._ [4 points]

The tests were helpful in ensuring the code functions as it is intended to and catching errors. The
most difficult part of the assignment was implementing the AlphaBetaPlayer class, it just took us a while
to conceptually understanding the Alpha-Beta pruning search algorithm. The most rewarding part was being
able to conceptually understand Minimax and the Alpha-Beta Pruning. I learned the most about the Minimax algorithm.
