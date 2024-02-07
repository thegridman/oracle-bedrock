/*
 * File: ProfilesTest.java
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * The contents of this file are subject to the terms and conditions of 
 * the Common Development and Distribution License 1.0 (the "License").
 *
 * You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the License by consulting the LICENSE.txt file
 * distributed with this file, or by consulting https://oss.oracle.com/licenses/CDDL
 *
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file LICENSE.txt.
 *
 * MODIFICATIONS:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 */

package com.oracle.bedrock.runtime;

import com.oracle.bedrock.Option;
import com.oracle.bedrock.OptionsByType;
import org.junit.AfterClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests for {@link Profile}s.
 * <p>
 * Copyright (c) 2016. All Rights Reserved. Oracle Corporation.<br>
 * Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
 *
 * @author Brian Oliver
 */
public class ProfilesTest
{
    @AfterClass
    public static void cleanup()
    {
        System.getProperties().remove("bedrock.profile.example");
    }

    /**
     * Ensure that we can load and instantiate Profiles.
     */
    @Test
    public void shouldReturnCreateProfiles()
    {
        System.setProperty("bedrock.profile.example", "hello");
        System.setProperty("bedrock.profile.example.classname", ExampleProfile.class.getName());

        OptionsByType optionsByType = Profiles.getProfiles();

        assertThat(optionsByType, is(not(nullValue())));
        assertThat(optionsByType.asArray().length, is(2));

        ExampleProfile profile = optionsByType.get(ExampleProfile.class);
        assertThat(profile, is(not(nullValue())));
        assertThat(profile.getParameters(), is("hello"));

        CustomProfile customProfile = optionsByType.get(CustomProfile.class);
        assertThat(customProfile, is(not(nullValue())));
    }

    /**
     * A custom profile which will be loaded via the ServiceLoader.
     */
    public static class CustomProfile
            implements Profile, Option
        {
        @Override
        public void onLaunching(Platform platform, MetaClass metaClass, OptionsByType optionsByType)
            {
            }

        @Override
        public void onLaunched(Platform platform, Application application, OptionsByType optionsByType)
            {
            }

        @Override
        public void onClosing(Platform platform, Application application, OptionsByType optionsByType)
            {
            }
        }

    /**
     * A custom profile which will be loaded via the ServiceLoader, but is not an Option.
     */
    public static class NonOptionCustomProfile
            implements Profile
        {
        @Override
        public void onLaunching(Platform platform, MetaClass metaClass, OptionsByType optionsByType)
            {
            }

        @Override
        public void onLaunched(Platform platform, Application application, OptionsByType optionsByType)
            {
            }

        @Override
        public void onClosing(Platform platform, Application application, OptionsByType optionsByType)
            {
            }
        }

}
