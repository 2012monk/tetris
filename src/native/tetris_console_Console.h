/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class tetris_console_Console */

#ifndef _Included_tetris_console_Console
#define _Included_tetris_console_Console
#ifdef __cplusplus
extern "C" {
#endif
#undef tetris_console_Console_DEFAULT_CLEAR_UNIT
#define tetris_console_Console_DEFAULT_CLEAR_UNIT 32L
#undef tetris_console_Console_DEFAULT_COLOR
#define tetris_console_Console_DEFAULT_COLOR -1L
/*
 * Class:     tetris_console_Console
 * Method:    setColorPair
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_tetris_console_Console_setColorPair
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     tetris_console_Console
 * Method:    getScreenWidth
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_tetris_console_Console_getScreenWidth
  (JNIEnv *, jclass);

/*
 * Class:     tetris_console_Console
 * Method:    getScreenHeight
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_tetris_console_Console_getScreenHeight
  (JNIEnv *, jclass);

/*
 * Class:     tetris_console_Console
 * Method:    readBytes
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_tetris_console_Console_readBytes
  (JNIEnv *, jclass);

/*
 * Class:     tetris_console_Console
 * Method:    drawChar
 * Signature: (IICII)V
 */
JNIEXPORT void JNICALL Java_tetris_console_Console_drawChar
  (JNIEnv *, jclass, jint, jint, jchar, jint, jint);

/*
 * Class:     tetris_console_Console
 * Method:    drawBorder
 * Signature: (IIIIII)V
 */
JNIEXPORT void JNICALL Java_tetris_console_Console_drawBorder
  (JNIEnv *, jclass, jint, jint, jint, jint, jint, jint);

/*
 * Class:     tetris_console_Console
 * Method:    drawString
 * Signature: (IILjava/lang/String;II)V
 */
JNIEXPORT void JNICALL Java_tetris_console_Console_drawString
  (JNIEnv *, jclass, jint, jint, jstring, jint, jint);

/*
 * Class:     tetris_console_Console
 * Method:    clearArea
 * Signature: (IIIICII)V
 */
JNIEXPORT void JNICALL Java_tetris_console_Console_clearArea
  (JNIEnv *, jclass, jint, jint, jint, jint, jchar, jint, jint);

/*
 * Class:     tetris_console_Console
 * Method:    clearLine
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_tetris_console_Console_clearLine
  (JNIEnv *, jclass, jint);

/*
 * Class:     tetris_console_Console
 * Method:    clearScreen
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_tetris_console_Console_clearScreen
  (JNIEnv *, jclass);

/*
 * Class:     tetris_console_Console
 * Method:    init
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_tetris_console_Console_init
  (JNIEnv *, jclass);

/*
 * Class:     tetris_console_Console
 * Method:    shutdown
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_tetris_console_Console_shutdown
  (JNIEnv *, jclass);

/*
 * Class:     tetris_console_Console
 * Method:    refresh
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_tetris_console_Console_refresh
  (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif
