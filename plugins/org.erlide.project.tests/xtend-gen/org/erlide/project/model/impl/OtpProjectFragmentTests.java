package org.erlide.project.model.impl;

import org.erlide.project.model.ErlangModelException;
import org.erlide.project.model.impl.CodeFolder;
import org.erlide.project.model.impl.OtpProjectFragment;
import org.junit.Test;

@SuppressWarnings("all")
public class OtpProjectFragmentTests {
  @Test
  public void createOtpFragment() {
    OtpProjectFragment _otpProjectFragment = new OtpProjectFragment(null, "myFrag");
    final OtpProjectFragment fragment = _otpProjectFragment;
    CodeFolder _codeFolder = new CodeFolder();
    final CodeFolder folder = _codeFolder;
    fragment.addSourceFolder(folder);
  }
  
  @Test(expected = ErlangModelException.class)
  public void createBadOtpFragment() {
    OtpProjectFragment _otpProjectFragment = new OtpProjectFragment(null, "myFrag");
    final OtpProjectFragment fragment = _otpProjectFragment;
    CodeFolder _codeFolder = new CodeFolder();
    final CodeFolder folder = _codeFolder;
    fragment.addSourceFolder(folder);
    fragment.addSourceFolder(folder);
  }
}
