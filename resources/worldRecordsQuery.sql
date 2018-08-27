use Sokoban

create table World_Records
(
	Player_Username nvarchar(30) not null,
	Level_Name nvarchar(25) not null,
	Player_steps int not null,
	Player_seconds int not null
)