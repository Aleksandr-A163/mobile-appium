package ru.otus.mobile.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.otus.mobile.extensions.DriverExtension;
import ru.otus.mobile.extensions.GuiceExtension;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({GuiceExtension.class, DriverExtension.class})
public @interface MobileTest {}
