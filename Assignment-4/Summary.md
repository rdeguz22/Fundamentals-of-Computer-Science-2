# SUMMARY

Please read and follow the assignment instructions on Canvas. Please put your answers here after the italicized
instructions (indicated by underscores).

## List timing questions [9 points]

### Which type of list is faster for adding to the front? Why?

_Please explain in a sentence or two._ [3 points]

The linked list is faster for adding to the front because the linked list only has to update references,
while the array list has to shift down each element in the list.

### Which type of list is faster for accessing a random element? Why?

_Please explain in a sentence or two._ [3 points]

The array list is faster for accessing a random element because array lists can access any index directly.

### Why is Dataset an abstract class?

_Please explain in a sentence or two._ [3 points]

Dataset is an abstract class because it serves as the base class for the subclasses ArrayListDataSet and
LinkedListDataSet.

## Reflection questions [10 points]

### What are the GitHub team name, user names, and repository URL?

_We need this information to grade your assignment._ [1 point]

homework-4-hw4-rico-doug, rdequz22 & dougconrad, https://github.com/Fundies-2-Oakland/homework-4-hw4-rico-doug.git

### Did you successfully implement everything that was requested?

_Answer "Yes", or state here which parts did not work or which tests did not pass._ [1 point]

Yes.

### How long did the assignment take?

_Provide the number of hours or minutes. Rather than giving a range, if you are unsure, give the average of the range so
we can enter the estimate in a spreadsheet._
[1 point]

3 hours.

### Whom did you work with and how?

_Discussing the assignment with people not on your team is fine as long as you don't share code. Please state whether
you attended any office hours. We especially like hearing about helpful TAs._ [1 point]

Pair Progamming with Ricardo DeGuzman and Douglas Conrad. I did not attend office hours.

### What was your AI usage?

_State explicitly (yes or no) whether you used AI. If so, say which tools you used and what your prompts were. Your
assignment will not be graded if you do not answer this question._ [1 point]

We did not use AI.

### What other resources did you use?

_Please give specific URLs (not "Stack Overflow" or "Google") and state which ones were particularly helpful. You do not
need to list course materials._ [1 point]

Slides from class during the previous week.

### Test coverage

_Did your tests cover every line of the `addToFront()`s, `getRandomRow()`,
`sortDataset()`, and `Row`'s `compareTo()` (and any helper methods you
created for them)? Did you submit a screenshot? If not, please explain._ [1 point]

Yes they did, and screenshots have been submitted.

### Reflections

_Give **one or more paragraphs** reflecting on your experience with the assignment, including answers to **all** of
these questions:_

- _What was the most difficult part of the assignment?_
- _What was the most rewarding part of the assignment?_
- _What did you learn doing the assignment?_

_Constructive and actionable suggestions for improving assignments, office hours, and lecture are always welcome, as is
feedback about TAs._ [3 points]

The most difficult part of the assignment was making sure our tests had 100% code coverage. The most rewarding part of
the
assignment was getting more practice writing effective tests. It was very helpful to see what lines were not covered, as
with the IllegalStateException in getRandomMeow(). I learned the different behaviors of different types
of lists doing this assignment.