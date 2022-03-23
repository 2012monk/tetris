# TETRIS

tetris implemented with java

## features

- in game score board
- presenting next block
- global score board

## UI

### 게임 화면

- [x] game board
- [x] score board
- [x] next block notification
- [x] timer
- [ ] game over screen
- [ ] leader board

## function list

### menu select

-[x] play
-[ ] score board
-[x] quit

### user input

-[x] get single char
-[x] get arrow input
-[x] down, up, rotate, force drop
-[x] pause
-[x] quit

### print

-[x] refresh board
-[x] print single block
-[x] print board
-[x] print blocks in different color

### tetris

- [x] drop a block at a specified speed
- [x] move block left, right
- [x] rotate block
- [x] wall kick
- [x] collision detection
- [ ] complete row effect
- [x] game end condition check

## Console Management (TUI)

JNI 를 이용한 ncurses 라이브러리를 통해 터미널 제어

- [x] create new window
- [x] non blocking input handle
- [x] paint border
- [ ] paint border with character
- [x] move window position

## TODO

- 키 입력시 escape 코드 출력되는 버그 수정
- 화면이 가끔씩 밀려서 갱신되는 버그 수정
- 화면 갱신 순서 sync
- decoupling
- 상속구조 리팩터링
- 과도한 리소스 사용 수정
- UI 로직 분리
- 회전 로직시 깜빡임
- game end detect 로직 버그 수정