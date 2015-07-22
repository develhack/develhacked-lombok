package com.develhack.lombok.transform;

import java.io.File;

import lombok.DirectoryRunner;

import org.junit.runner.RunWith;

@RunWith(DirectoryRunner.class)
public class TestLombokFilesIdempotent extends DirectoryRunner.TestParams {
	@Override
	public File getBeforeDirectory() {
		return getAfterDirectory();
	}

	@Override
	public File getAfterDirectory() {
		return new File("test/transform/develhack/after-delombok");
	}

	@Override
	public File getMessagesDirectory() {
		return new File("test/transform/develhack/messages-idempotent");
	}

	@Override
	public DirectoryRunner.Compiler getCompiler() {
		return DirectoryRunner.Compiler.DELOMBOK;
	}

	@Override
	public boolean printErrors() {
		return true;
	}
}
