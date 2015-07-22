package com.develhack.lombok.transform;

import java.io.File;

import lombok.DirectoryRunner;

import org.junit.runner.RunWith;

@RunWith(DirectoryRunner.class)
public class TestWithDelombok extends DirectoryRunner.TestParams {
	@Override
	public DirectoryRunner.Compiler getCompiler() {
		return DirectoryRunner.Compiler.DELOMBOK;
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
		return new File("test/transform/develhack/after-delombok");
	}

	@Override
	public File getMessagesDirectory() {
		return new File("test/transform/develhack/messages-delombok");
	}
}
