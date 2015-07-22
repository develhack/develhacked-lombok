package com.develhack.lombok.transform;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestLombokFilesIdempotent.class, TestWithDelombok.class, TestWithEcj.class})
public class RunTransformTests {
}
