# TETRIS

tetris implemented with java

## features

- in game score board
- presenting next block
- global score board

## UI

### 게임 화면

- game board
- score board
- next block notification
- timer

## function list

### menu select

  - play
  - score board
  - quit

### user input

  - get single char
  - get arrow input
  - down, up, rotate, force drop
  - pause
  - quit

### print

- refresh board
- print single block
- print board
- print blocks in different color

### tetris

- drop a block at a specified speed
- move block left, right
- rotate block
- wall kick
- collision detection
- complete row effect
- game end condition check


## Console Management (TUI)

JNI 를 이용한 ncurses 라이브러리를 통해 터미널 제어

- create new window
- non blocking input handle
- paint border
- paint border with character
- move window position

## TODO

- 키 입력시 escape 코드 출력되는 버그 해결