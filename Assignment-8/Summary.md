# Summary.md

Answer all the questions. Please put your answers _after_ the
italicized instructions, as shown in the
[video](https://northeastern.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=fc8041ab-6ae4-4298-89b6-b2980164c325).

## Planning questions

### Partial solutions (5 points)

* During recursive backtracking, there is a partial solution that is
  passed forward.
* For Sudoku and the N-Queens Problem, it was a partly completed board.
* For the Anagram Solver, it was words that had been applied to the input string.
  For example, if the input string was "husky bear", a partial solution might be
  ["hub", "sky"].

**What is the partial solution for this problem?**

_State both the general answer, and provide a specific example for the
situation in the cartoon (like we did for the anagram solver)._

A partial solution to this problem is some appetizers with prices adding up to less than $15.05.
For example, a partial solution may be ["Mixed Fruit", "French Fries"].

### Legal moves (5 points)

* For Sudoku, legal moves were found by iterating over the digits 0-9 and
  determining if that digit could be placed in the first empty square without
  conflicting with any digits placed earlier.
* For the N-Queens problem, legal moves were found by iterating over row numbers
  and determining if a queen could be placed in that row of the current column
  without threatening a placed queen.
* For the Anagram Solver, we iterated over all the words in the dictionary and
  rejected words that could not be formed from letters in the initial string
  that had not yet been matched.
    * To continue the example of finding an anagram for "husky bear", after adding
      "hub" and "sky" to the partial solution, the remaining letters were "ear".
    * Consequently, the word "acre" would not be a legal move, but "ear" would be.

**How is a legal move determined for this problem?**

_State both the general answer, and provide a specific example._

For this problem, legal moves are found by iterating over the items on the menu and
determining if that item could be added to our list without preventing a total of $15.05
from being achieved. If we have ["Side Salad", "Hot Wings", "Sampler Plate"], adding
"Mixed Fruit" would be a legal move, but adding "Mozzarella Sticks" would not.

### Termination condition (5 points)

* Recursive backtracking requires a termination condition to know when we have
  found a solution. (For now, we will assume that a legal solution exists.)
* For Sudoku, the termination condition is when all the squares contain a digit.
* For the N-Queens Problem, it is when the column number is off the edge of the
  board (implying that all of the columns on the board have been filled).
* For the Anagram Solver, it is when all the words in the initial string have been matched.

**What is the termination condition for this problem?**

_In other words, how do we know when a partial solution is complete?_

The termination condition for this problem is when the price of all of the items in our list
is equal to $15.05.

## Summary questions

### Where can we find your work on github?

_Give the usernames of all teammates and the HTTPS URL of the repository._ [1 point]

dougconrad &
rdeguz22. https://github.com/Fundies-2-Oakland/homework-8-recursive-backtracking-doug-rico-1.git

### Does everything work, to the best of your knowledge? This includes autograder tests.

_Answer "Yes", or state here which parts did not work or which tests did not pass._ [1 point]

Yes.

## How long do you estimate the assignment took? [1 point]

_Estimate the number of hours or minutes. Rather than giving a range, if you are unsure,
give the average of the range so we can enter the estimate in a spreadsheet._

2.5 hours.

### Who did you work with and how?

_Discussing the assignment with people not on your team is fine as long as you
don't share code._ [1 point]

Pair programming with Ricardo de Guzman and Douglas Conrad.

## What was your AI usage? [1 point]

_State explicitly (yes or no) whether you used AI. If so, say which tools you
used and what your prompts were. Your assignment will not be graded if you do
not answer this question._

No AI used.

### What has your Office Hours experience been?

_If you have attended Office Hours after the first assignment, what was your
experience? We especially welcome appreciation of TAs by name or suggestions
for better training TAs. If you have not attended Office Hours, why not?_ [1 point]

I have not attended office hours, I have not had the need to.

### What resources did you use?

_Please give specific URLs (not just "Stack Overflow" or "Baeldung") and
state which ones were particularly helpful._ [1 point]

Lecture slides from class and the supplemental links included in the assignment instructions.

### Reflections

Give one or more paragraphs reflecting on your experience with the
assignment, including answering all of these questions:

* What was the most difficult part of the assignment?
* What was the most rewarding part of the assignment?
* What did you learn doing the assignment?

_Constructive and actionable suggestions for improving assignments, office hours, and
lecture are always welcome._ [3 points]

The most difficult part of the assignment was implementing the first solver, findOneSolution().
The most rewarding part of the assignment was seeing all our tests pass. I learned how to use
recursive backtracking doing this assignment.
