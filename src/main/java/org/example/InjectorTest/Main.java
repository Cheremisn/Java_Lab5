package org.example.InjectorTest;

import org.example.SomeBean;

public class Main {
    public static void main(String[] args) throws Exception {
        SomeBean someBean = (new Injector()).inject(new SomeBean());
        someBean.foo();

    }
}