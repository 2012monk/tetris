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
- [x] game over screen
- [x] leader board
- [x] block counter

## function list

### menu select

-[x] play
-[x] score board
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

1. __상속구조 리팩터링__
2. UI 로직 분리

- ascii key code 크로스 플랫폼 체크
- 화면 갱신 작업 단위
- 화면이 가끔씩 밀려서 갱신되는 버그 수정
- decoupling
- 상속구조 리팩터링
- UI 로직 분리
- ascii key code 크로스 플랫폼 체크
- 블록이 쌓이기 전 가이드와 겹치는 이슈
- 반응성 개선