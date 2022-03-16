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

JNIEXPORT jint JNICALL Java_tetris_console_Console_readBytes (JNIEnv *env, jclass clazz) {
  return getch();
}

JNIEXPORT jint JNICALL Java_tetris_console_Console_getMaxWidth (JNIEnv *env, jobject obj) {
  return COLS;
}

JNIEXPORT jint JNICALL Java_tetris_console_Console_getMaxHeight (JNIEnv *env, jobject obj) {
  return LINES;
}

JNIEXPORT void JNICALL Java_tetris_console_Console_shutdown (JNIEnv *env, jobject obj) {
  endwin();
}

JNIEXPORT void JNICALL Java_tetris_console_Console_clearLine (JNIEnv *env, jobject obj, jint x, jint y) {
  move(y, 0);
  clrtoeol();
  endDraw();
}

JNIEXPORT void JNICALL Java_tetris_console_Console_init (JNIEnv *env, jobject obj) {
  initscr();
  curs_set(0);
  keypad(stdscr, TRUE);
  cbreak();
  noecho();
  clear();
  refresh();
}

JNIEXPORT void JNICALL Java_tetris_console_Console_refresh (JNIEnv *env, jclass clazz) {
  refresh();
}

JNIEXPORT void JNICALL Java_tetris_console_Console_drawBorder (JNIEnv *env, jclass clazz, jint x, jint y, jint width, jint height) {
  drawCorners(x, y, width, height);
  drawHorizontalLine(x, y, width, height);
  drawVerticalLine(x, y, width, height);
  endDraw();
}

JNIEXPORT void JNICALL Java_tetris_console_Console_drawChar (JNIEnv *env, jclass clazz, jint x, jint y, jchar chr) {
  mvaddch(y, x, chr);
  endDraw();
}

void printString(int x, int y, int width, int height, char str[]) {

}

JNIEXPORT void JNICALL Java_tetris_console_Console_drawString (JNIEnv *env, jclass clazz, jint x, jint y, jstring str) {
  const char *cString = (*env)->GetStringUTFChars(env, str, NULL);
  mvaddstr(y, x, cString);
  (*env)->ReleaseStringUTFChars(env, str, cString);
  endDraw();
}
JNIEXPORT void JNICALL Java_tetris_console_Console_clearArea (JNIEnv *env, jclass clazz, jint x, jint y, jint width, jint height) {
  for (int i = x; i < x + height; i++) {
    for (int j = y; j < y + width; j++) {
      mvaddch(i, j, ' ');
    }
  }
  endDraw();
}

JNIEXPORT void JNICALL Java_tetris_console_Console_clearScreen (JNIEnv *env, jclass clazz) {
  clear();
  endDraw();
}
