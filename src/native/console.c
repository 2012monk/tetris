#include "tetris_console_Console.h"
#include <ncurses.h>

static int inAction = 0;

void endDraw() {
  if (!inAction) {
    move(0, 0);
    refresh();
  }
}

void drawHorizontalLine(int x, int y, int width, int height) {
  mvhline(y, x + 1, ACS_HLINE, width - 1);
  mvhline(y + height, x + 1, ACS_HLINE, width - 1);
}

void drawVerticalLine(int x, int y, int width, int height) {
  mvvline(y + 1, x, ACS_VLINE, height - 1);
  mvvline(y + 1, x + width, ACS_VLINE, height - 1);
}

void drawCorners(int x, int y, int width, int height) {
  mvaddch(x, y, ACS_ULCORNER);
  mvaddch(x + height, y, ACS_LLCORNER);
  mvaddch(x, y + width, ACS_URCORNER);
  mvaddch(x + height, y + width, ACS_LRCORNER);
}

JNIEXPORT jint JNICALL Java_tetris_console_Console_readBytes (JNIEnv *, jclass) {
  return getch();
}

JNIEXPORT jint JNICALL Java_tetris_console_Console_getMaxWidth (JNIEnv *, jobject) {
  return COLS;
}

JNIEXPORT jint JNICALL Java_tetris_console_Console_getMaxHeight (JNIEnv *, jobject) {
  return LINES;
}

JNIEXPORT void JNICALL Java_tetris_console_Console_shutdown (JNIEnv *, jobject) {
  endwin();
}

JNIEXPORT jchar JNICALL Java_tetris_console_Console_getChar (JNIEnv *, jobject) {
  initscr();
  cbreak();
  char c = getch();
  endwin();
  return c;
}

JNIEXPORT void JNICALL Java_tetris_console_Console_writeChar (JNIEnv *, jobject, jint x, jint y, jchar chr) {
  move(y, x);
  addch(chr);
  endDraw();
}

JNIEXPORT void JNICALL Java_tetris_console_Console_clearLine (JNIEnv *, jobject, jint x, jint y) {
  move(y, 0);
  clrtoeol();
  endDraw();
}

JNIEXPORT void JNICALL Java_tetris_console_Console_init (JNIEnv *, jobject) {
  initscr();
  curs_set(0);
  keypad(stdscr, TRUE);
  cbreak();
  noecho();
  clear();
  refresh();
}

JNIEXPORT void JNICALL Java_tetris_console_Console_refresh (JNIEnv *, jclass) {
  refresh();
}

JNIEXPORT void JNICALL Java_tetris_console_Console_drawBorder (JNIEnv *, jclass, jint x, jint y, jint width, jint height) {
  drawCorners(x, y, width, height);
  drawHorizontalLine(x, y, width, height);
  drawVerticalLine(x, y, width, height);
}

JNIEXPORT void JNICALL Java_tetris_console_Console_drawChar (JNIEnv *, jclass, jint x, jint y, jchar chr) {
  mvaddch(y, x, chr);
}

void printString(int x, int y, int width, int height, char str[]) {

}
JNIEXPORT void JNICALL Java_tetris_console_Console_printString (JNIEnv *, jclass, jint, jint, jint, jstring);
