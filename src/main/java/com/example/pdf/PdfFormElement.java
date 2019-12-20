package com.example.pdf;

import com.example.pdf.enums.PdfElementType;
import lombok.Data;

@Data
public class PdfFormElement {


    //元素类型
    private PdfElementType type;

    private String field;

    private String text;

    private int fontSize = 12;

    private byte[] bytes;

    public PdfFormElement(PdfElementType type, String field, String text, int fontSize) {
        this.type = type;
        this.field = field;
        this.text = text;
        this.fontSize = fontSize;
    }

    public PdfFormElement(PdfElementType type, String field, String text) {
        this.type = type;
        this.field = field;
        this.text = text;
    }

    public PdfFormElement(PdfElementType type, String field, byte[] bytes) {
        this.type = type;
        this.field = field;
        this.bytes = bytes;
    }
}
