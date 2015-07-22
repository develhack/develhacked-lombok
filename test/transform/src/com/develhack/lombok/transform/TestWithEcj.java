package com.develhack.lombok.transform;

import java.io.File;

import lombok.DirectoryRunner;

import org.junit.runner.RunWith;

// You need to add the following vm-parameter to run the test:
// -javaagent:${project_loc:lombok}/dist/lombok.jar
// When running inside eclipse's junit tester, you don't actually need to run 'ant dist' after updating code, though.

@RunWith(DirectoryRunner.class)
public class TestWithEcj extends DirectoryRunner.TestParams {
	@Override
	public DirectoryRunner.Compiler getCompiler() {
		return DirectoryRunner.Compiler.ECJ;
	}

	@Override
	public boolean printErrors() {
		return true;
	}

	@Override
	public File getBeforeDirectory() {
		return new File("test/transform/develhack/before");
	}

	@Override
	public File getAfterDirectory() {
		return new File("test/transform/develhack/after-ecj");
	}

	@Override
	public File getMessagesDirectory() {
		return new File("test/transform/develhack/messages-ecj");
	}
}
