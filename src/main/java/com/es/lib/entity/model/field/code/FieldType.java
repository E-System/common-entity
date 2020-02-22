/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2015
 */

package com.es.lib.entity.model.field.code;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Arbitrary fields type
 *
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 26.05.15
 */
public enum FieldType {
    /**
     * Single line text field
     */
    TEXT {
        @Override
        public boolean isAnySymbol() {
            return true;
        }

        @Override
        public Collection<String> getAvailableView() {
            return Arrays.asList(
                IFieldView.Text.Single,
                IFieldView.Text.Many
            );
        }
    },
    /**
     * Number
     */
    NUMBER {
        @Override
        public boolean isNumberSymbol() {
            return true;
        }
    },
    /**
     * Sum
     */
    SUM {
        @Override
        public boolean isSum() {
            return true;
        }

        @Override
        public boolean isNumberSymbol() {
            return true;
        }
    },
    /**
     * Selector with one value (combo, radio or checkbox)
     */
    SELECTOR_SINGLE {
        @Override
        public Collection<String> getAvailableView() {
            return Arrays.asList(
                IFieldView.Selector.Holistic,
                IFieldView.Selector.Separate
            );
        }

        @Override
        public boolean isSelector() {
            return true;
        }
    },
    /**
     * Selector list (list, checkbox list)
     */
    SELECTOR_MANY {
        @Override
        public Collection<String> getAvailableView() {
            return Arrays.asList(
                IFieldView.Selector.Holistic,
                IFieldView.Selector.Separate,
                IFieldView.Selector.Menu
            );
        }

        @Override
        public boolean isSelector() {
            return true;
        }

        @Override
        public boolean isManyAvailable() {
            return true;
        }

        @Override
        public boolean isPositional() {
            return false;
        }
    },

    /**
     * Date
     */
    DATE {
        @Override
        public boolean isDate() { return true; }
    },

    /**
     * Address
     */
    ADDRESS {
        @Override
        public boolean isAddress() { return true; }

        @Override
        public Collection<String> getAvailableView() {
            return Arrays.asList(
                IFieldView.Address.Street,
                IFieldView.Address.Building
            );
        }

        @Override
        public boolean isPositional() {
            return false;
        }
    },

    /**
     * Hidden
     */
    HIDDEN {
        @Override
        public boolean isHidden() { return true; }

        @Override
        public boolean isPositional() {
            return false;
        }
    };

    /**
     * Multi value available (files, user fields, selectors)
     *
     * @return true - Multi value available
     */
    public boolean isManyAvailable() {
        return false;
    }

    /**
     * Available view types for render(for selectors)
     *
     * @return - Available view types for render
     */
    public Collection<String> getAvailableView() {
        return Collections.emptyList();
    }

    /**
     * Available default value
     *
     * @return true - Default value available
     */
    public boolean isDefaultValueAvailable() {
        return true;
    }

    /**
     * Is user type
     *
     * @return true - Is user type
     */
    public boolean isUserType() {
        return false;
    }

    /**
     * Is sum
     *
     * @return true - Is sum
     */
    public boolean isSum() {
        return false;
    }

    /**
     * Is file
     *
     * @return true - Is file
     */
    public boolean isFile() {
        return false;
    }

    /**
     * Allow any symbols
     *
     * @return true - Allow any symbols
     */
    public boolean isAnySymbol() {
        return false;
    }

    /**
     * Is number field (allow only numbers)
     *
     * @return true - Allow only numbers
     */
    public boolean isNumberSymbol() {
        return false;
    }

    /**
     * Is selector (combo, checkbox)
     *
     * @return true - Is selector
     */
    public boolean isSelector() {
        return false;
    }

    /**
     * Is date
     *
     * @return true - Is date
     */
    public boolean isDate() {
        return false;
    }

    /**
     * Is hidden
     *
     * @return true - Is hidden
     */
    public boolean isHidden() {
        return false;
    }

    /**
     * Is address
     *
     * @return true - Is address
     */
    public boolean isAddress() {
        return false;
    }

    /**
     * Can be positional on form
     *
     * @return true - Is positional
     */
    public boolean isPositional() {return true;}

}
