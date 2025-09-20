# SUMMARY

Please read and follow the directions, putting our answers _after_
the italicized instructions. It is a good idea to view your Summary.md
file in Preview mode before submitting it.

## Defining distance

In this assignment, we defined the weight of the edge between two nodes as the
geographical distance between the two stations.

What if we defined the edge weight some other way? For example, we could say,
"the Fruitvale BART is 15 minutes from here," and that would be a meaningful
weight. In that case, we would be defining the edge weight as the
number of minutes it takes to travel between the two nodes.

Let's discuss the pros and cons of various definitions of the edge weight.

Let's pretend that we are going to use these new edge weights to
build a new MST, keeping the stations where they are, and replace the
existing BART train tracks with the new tracks from the new MST.

### Practicality [3 points]

We could define the edge weight between two stations as the financial cost of
building the track between them (which is bigger if we have to dig a tunnel
underwater or through a mountain).

_Ethically, what are the pros and cons of using money as the edge weights to
build an MST to choose BART tracks?_

The pros of using money as the edge weights would be optimizing resource allocation, reducing
overall construction expenses, and enabling more expansion of the BART. The cons of this definition would be
the prioritization of cheaper routes, which would leave some communities underserved because of high construction
expenses.

### Privacy [3 points]

We could define the edge weight as 1/n, where n is the number of passengers who
regularly travel between the two stations. To count the number of passengers
who travel between two stations, we need to keep track of each passenger's
entrance and exit station. This would require asking the Clipper Card company
to give us the entrance and exit locations for each passenger.

_How could we protect people's privacy while still using that data?_

Defining the edge weight based on passenger traffic would lead to prioritization of the highest demand routes and
more efficient use of resources. However, it would lead to privacy concerns of tracking passenger entry and exit.
To protect people's privacy while still using that data, the data should be anonymized, ensuring individuals' travel
habits remain private.

### Fairness [3 points]

Traveling by BART costs different amounts of money, depending on
how far you're going. Additionally, passengers with low incomes can
receive discounted tickets.

What if we defined the edge weight between two stations as 1/n, where n
is the revenue from ticket sales from passengers travelling between
those two stations?

_How would you ensure that BART still services all communities, including
those with less money?_

Defining the edge weights based on ticket revenue would lead to prioritization of the routes that generate
the most income. However, it would disproportionately favor wealthier areas while neglecting lower income communities
that contribute less revenue. To ensure that BART still services all communities, a minimum service guarantee could be
implemented
so that every community receives transit coverage or an approach that considers revenue, ridership demand, and
government subsidies
could be implemented to help prevent underserved areas from being overlooked.

### Climate change [3 points]

We want to reduce traffic congestion and greenhouse gas emissions from cars
driving on the roads. Let's assume that car drivers will switch to BART if BART
is more convenient than driving.

_What definition could we use for the edge weights such that the MST will
select a system of BART tracks that would reduce the number of cars on the
road?_

A definition that could be used is the number of cars removed from the road if a BART line were introduced between
two stations. Another definition could be the reduction in carbon emissions by shifting people from cars
to public transit. These approaches would prioritize routes that alleviate traffic congestion and improve air quality.
However, this could overlook some underserved areas.

## Logistics

### Where can we find your work on github? [1 point]

_Give the usernames of all teammates and the HTTPS URL of the repository page._

Usernames: rdeguz22 & dougconrad URL: https://github.com/Fundies-2-Oakland/homework-10-mst-douglas-rico.git

### Did you successfully implement everything that was requested, and does everything work, to the best of your knowledge? This includes autograder tests.

_Answer "Yes", or state here which parts did not work or which tests did not pass.
This would also be the place to mention any optional milestones you completed._

Yes.

### What was the summary sentence output by the program for all stations? [1 point]

It should be of the form `The minimum spanning tree has __ edges and is __ miles long._

When NUM_STATIONS = 5: The minimum spanning tree has 4 edges and is 72 miles long.
When NUM_STATIONS = -1: The minimum spanning tree has 51 edges and is 120 miles long.

### How long do you estimate the assignment took? [1 point]

_Rather than giving a range, if you are unsure, give the averages of the range._

The assignment took around 2 hours.

### Who did you work with and how? [1 point]

_Discussing the assignment with people not on your team is fine as long as you
don't share code._ [1 points]

Pair programming with Ricardo de Guzman and Douglas Conrad.

### What was your AI usage? [1 point]

State explicitly (yes or no) whether you used AI. If so, say which tools you
used and what your prompts were. Your assignment will not be graded if you do
not answer this question.

We did not use AI on this assignment.

### What other resources did you use? [1 point]

_Please give specific URLs (not just "Stack Overflow" or "Baeldung") and
state which ones were particularly helpful._

We used class slides, more specifically the ones on trees and Kruskal's Algorithm. They were very helpful
in gaining an understanding on how to complete this assignment.

## Reflections [3 points]

Give **one or more paragraphs** reflecting on your experience with the
assignment, including answers to all of these questions:

* _What was the most difficult part of the assignment?_
* _What was the most rewarding part of the assignment?_
* _What did you learn doing the assignment?_

_Constructive and actionable suggestions for improving assignments, office
hours, and lecture are always welcome. If there was a TA who was especially
helpful, please share that._

The most difficult part of this assignment was implementing the KruskalIterator<T>, more specifically,
conceptually understanding Kruskal's Algorithm took some deep thinking. The most rewarding part was
being able to understand the Algorithm and seeing it work. I learned the most about trees and Kruskal's Algorithm in
this assignment.

