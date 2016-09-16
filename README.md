# NHL-drafter
a program for drafting the best team that you can in your hockey pool

The assumption here will be that the computer is comparing your team to an impossibly-good team and trying
to minimize the distance from your team and this theoretical team. The comparison "team" is not actually a team
of players but a team of stats (e.g. Alex Ovechkin's 50 goals and Erik Karlsson's 66 assists). 

1) build database of all players in league
2) allow user to select which stats will be employed (assuming head to head matchup where having more of that stat means one point that week)
3) scan through all the players in the database to find the top 5 that will minimize the distance
4) user selects player, stats are updated
5) go to 3

This work will somehow have to account for the fact that goalies and players are in mutually exclusive categories,
i.e., a player does not have GAA and a goalie does not have blocks.


This might not work best in an OO language like java (I'd prefer to use C or python), but I need to learn Java, so here we go.