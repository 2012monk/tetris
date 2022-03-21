#include "tetris_console_Console.h"
#include <ncurses.h>

static int inAction = 0;
int COLOR_COUNT = 8;

void endDraw() {
  if (!inAction) {
    move(0, 0);
    refresh();
  }
}

JNIEXPORT void JNICALL Java_tetris_console_Console_endDraw (JNIEnv *env, jclass clazz) {
  if (inAction > 0) {
    inAction--;
  }
  endDraw();
}

JNIEXPORT void JNICALL Java_tetris_console_Console_startDraw (JNIEnv *env, jclass clazz) {
  inAction++;
}

void drawHorizontalLine(int x, int y, int width, int height) {
  mvhline(x, y, ACS_HLINE, width);
  mvhline(x + height, y, ACS_HLINE, width);
}

void drawVerticalLine(int x, int y, int width, int height) {
  mvvline(x, y, ACS_VLINE, height);
  mvvline(x, y + width, ACS_VLINE, height);
}

void drawCorners(int x, int y, int width, int height) {
  mvaddch(x, y, ACS_ULCORNER);
  mvaddch(x + height, y, ACS_LLCORNER);
  mvaddch(x, y + width, ACS_URCORNER);
  mvaddch(x + height, y + width, ACS_LRCORNER);
}

int setColorPair(int fg, int bg) {
  int number = fg + bg * COLOR_COUNT;
  init_pair(number, fg, bg);
  attron(COLOR_PAIR(number));
  return number;
}

void unsetColorPair(int number) {
  attroff(COLOR_PAIR(number));
}

JNIEXPORT jint JNICALL Java_tetris_console_Console_readBytes (JNIEnv *env, jclass clazz) {
  return getch();
}

JNIEXPORT jint JNICALL Java_tetris_console_Console_getScreenWidth (JNIEnv *env, jobject obj) {
  return COLS;
}

JNIEXPORT jint JNICALL Java_tetris_console_Console_getScreenHeight (JNIEnv *env, jobject obj) {
  return LINES;
}

JNIEXPORT void JNICALL Java_tetris_console_Console_shutdown (JNIEnv *env, jobject obj) {
  endwin();
}

JNIEXPORT void JNICALL Java_tetris_console_Console_clearLine (JNIEnv *env, jobject obj, jint x) {
  move(x, 0);
  clrtoeol();
  endDraw();
}

JNIEXPORT void JNICALL Java_tetris_console_Console_init (JNIEnv *env, jobject obj) {
  initscr();
  curs_set(0);
  keypad(stdscr, TRUE);
  cbreak();
  noecho();
  if (has_colors()) {
    use_default_colors();
    start_color();
  }
  attron(A_BOLD|A_DIM|A_BLINK);
  clear();
  refresh();
}

JNIEXPORT void JNICALL Java_tetris_console_Console_refresh (JNIEnv *env, jclass clazz) {
  refresh();
}

JNIEXPORT void JNICALL Java_tetris_console_Console_drawBorder (JNIEnv *env, jclass clazz, jint x, jint y, jint width, jint height, jint fg, jint bg) {
  width--;
  height--;
  drawHorizontalLine(x, y, width, height);
  drawVerticalLine(x, y, width, height);
  drawCorners(x, y, width, height);
  endDraw();
}

JNIEXPORT void JNICALL Java_tetris_console_Console_drawChar (JNIEnv *env, jclass clazz, jint x, jint y, jchar chr, jint fg, jint bg) {
  int n = setColorPair(fg, bg);
  mvaddch(x, y, chr);
  unsetColorPair(n);
  endDraw();
}

JNIEXPORT void JNICALL Java_tetris_console_Console_drawString (JNIEnv *env, jclass clazz, jint x, jint y, jstring str, jint fg, jint bg) {
  const char *cString = (*env)->GetStringUTFChars(env, str, NULL);
  mvaddstr(x, y, cString);
  (*env)->ReleaseStringUTFChars(env, str, cString);
  endDraw();
}

JNIEXPORT void JNICALL Java_tetris_console_Console_clearArea (JNIEnv *env, jclass clazz, jint x, jint y, jint width, jint height, jchar chr, jint fg, jint bg) {
  int n = setColorPair(fg, bg);
  // start inclusive, end exclusive
  for (int i = x; i < x + height; i++) {
    for (int j = y; j < y + width ; j++) {
      mvaddch(i, j, chr);
    }
  }
  unsetColorPair(n);
  endDraw();
}

JNIEXPORT void JNICALL Java_tetris_console_Console_clearScreen (JNIEnv *env, jclass clazz) {
  clear();
  endDraw();
}