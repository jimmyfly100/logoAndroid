package com.salieri.baselib.core;

import com.salieri.baselib.type.NUM;

public interface ILogoEngine {
    void FD(NUM value);
    void BK(NUM value);
    void RT(NUM value);
    void LT(NUM value);
    void error(String msg);
    void PU();
    void PD();
    void SETH(NUM value);
    void SETXY(NUM x, NUM y);
    void PPT();
    void PX();
    void drawTurtle();
    void saveAllFunc();
    void loadAllFunc();

}
