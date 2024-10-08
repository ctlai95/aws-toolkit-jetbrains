<!-- Copyright 2024 Amazon.com, Inc. or its affiliates. All Rights Reserved. -->
<!-- SPDX-License-Identifier: Apache-2.0 -->

<template>
    <div class="item-container" :class="{ selected: isSelected }" @click="toggleSelection" @focus="toggleSelectionOnFocus" tabindex="0">
        <div class="icon">
            <svg
                v-if="loginType === LoginOption.BUILDER_ID"
                width="20"
                height="20"
                viewBox="0 0 16 16"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
            >
                <path
                    fill-rule="evenodd"
                    clip-rule="evenodd"
                    d="M8 2C6.68228 2 5.61 3.07228 5.61 4.39C5.61 5.70772 6.68228 6.78 8 6.78C9.31771 6.78 10.39 5.70772 10.39 4.39C10.39 3.07228 9.31772 2 8 2ZM3.61 4.39C3.61 1.96772 5.57771 0 8 0C10.4223 0 12.39 1.96771 12.39 4.39C12.39 5.66447 11.8453 6.81311 10.9762 7.61551C11.1666 7.71116 11.3535 7.81608 11.5368 7.93001C13.5215 9.15787 15 11.3598 15 14V15H1V14C1 11.147 2.63919 8.7213 4.99701 7.59061C4.14356 6.78943 3.61 5.65134 3.61 4.39ZM10.482 9.62922C9.71501 9.15233 8.88729 8.90797 8.00229 8.91L8 8.91C5.59623 8.91 3.5507 10.634 3.09474 13H12.8975C12.601 11.5742 11.6867 10.3744 10.484 9.63047L10.482 9.62922Z"
                    fill="white"
                />
            </svg>
            <svg
                v-if="loginType === LoginOption.ENTERPRISE_SSO"
                width="20"
                height="20"
                viewBox="0 0 16 16"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
            >
                <path
                    fill-rule="evenodd"
                    clip-rule="evenodd"
                    d="M4 3C3.44772 3 3 3.44772 3 4C3 4.55229 3.44772 5 4 5C4.55228 5 5 4.55229 5 4C5 3.44772 4.55228 3 4 3ZM1 4C1 2.34315 2.34315 1 4 1C5.65685 1 7 2.34315 7 4C7 5.65676 5.65701 6.99984 4.00029 7C6.21244 7.00016 8 8.78781 8 11C8 8.78772 9.78771 7 12 7C14.2123 7 16 8.78772 16 11V14C16 14.5523 15.5523 15 15 15H9C8.44771 15 8 14.5523 8 14C8 14.5523 7.55228 15 7 15H1C0.447715 15 0 14.5523 0 14V11C0 8.78772 1.78801 7 4.00029 7C2.34344 7 1 5.65685 1 4ZM12 7C10.3431 7 9 5.65685 9 4C9 2.34315 10.3431 1 12 1C13.6569 1 15 2.34315 15 4C15 5.65685 13.6569 7 12 7ZM11 4C11 3.44772 11.4477 3 12 3C12.5523 3 13 3.44772 13 4C13 4.55229 12.5523 5 12 5C11.4477 5 11 4.55229 11 4ZM10 11C10 9.89228 10.8923 9 12 9C13.1077 9 14 9.89228 14 11V13H10V11ZM2 11C2 9.89228 2.89228 9 4 9C5.10772 9 6 9.89228 6 11V13H2V11Z"
                    fill="white"
                />
            </svg>
            <svg
                v-if="loginType === LoginOption.IAM_CREDENTIAL"
                width="15"
                height="15"
                viewBox="0 0 15 15"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
            >
                <path
                    d="M10.36 1.06667C11.1778 1.24444 11.8889 1.61778 12.4933 2.18667C13.1333 2.75556 13.56 3.43111 13.7733 4.21333C13.9867 4.96 14.0222 5.72444 13.88 6.50667C13.6667 7.53778 13.1333 8.39111 12.28 9.06667C11.4622 9.74222 10.52 10.08 9.45333 10.08C8.99111 10.08 8.56444 10.0089 8.17333 9.86667L7.37333 10.8267L7 10.9867H5.98667V12.48L5.50667 13.0133H4.01333V14.5067L3.48 14.9867H0.493333L0.0133333 14.5067V12.2133L0.12 11.84L5.13333 6.88C4.99111 6.41778 4.92 5.93778 4.92 5.44C4.92 4.8 5.06222 4.17778 5.34667 3.57333C5.66667 2.93333 6.07556 2.41778 6.57333 2.02667C7.10667 1.6 7.69333 1.31556 8.33333 1.17333C9.00889 0.995556 9.68445 0.96 10.36 1.06667ZM11.6933 8.26667C12.3333 7.76889 12.7422 7.11111 12.92 6.29333C13.0267 5.68889 12.9911 5.10222 12.8133 4.53333C12.6711 3.96444 12.3867 3.46667 11.96 3.04C11.5333 2.61333 11.0356 2.32889 10.4667 2.18667C9.89778 2.00889 9.31111 1.97333 8.70667 2.08C7.92444 2.25778 7.26667 2.66667 6.73333 3.30667C6.2 3.94667 5.93333 4.67556 5.93333 5.49333C5.89778 5.95556 5.96889 6.4 6.14667 6.82667L6.04 7.36L1.02667 12.4267V14.0267H3V12.48L3.48 12H5.02667V10.5067L5.50667 10.0267H6.73333L7.64 9.01333L8.17333 8.85333C8.6 8.99556 9.02667 9.06667 9.45333 9.06667C10.2711 9.06667 11.0178 8.8 11.6933 8.26667ZM10.84 5.54667C10.9467 5.40444 11 5.24444 11 5.06667C11.0356 4.88889 11 4.72889 10.8933 4.58667C10.8222 4.40889 10.7156 4.26667 10.5733 4.16C10.4311 4.05333 10.2533 4 10.04 4C9.86222 3.96444 9.68444 4 9.50667 4.10667C9.36445 4.17778 9.25778 4.28444 9.18667 4.42667C9.04444 4.67556 8.99111 4.94222 9.02667 5.22667C9.09778 5.47556 9.24 5.67111 9.45333 5.81333C9.70222 5.95556 9.95111 6.00889 10.2 5.97333C10.4489 5.90222 10.6622 5.76 10.84 5.54667Z"
                    fill="#C5C5C5"
                />
            </svg>
        </div>
        <div class="text">
            <div class="item-title">{{ itemTitle }}</div>
            <div class="p">{{ itemText }}</div>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import {LoginIdentifier} from "../..//model";
import login from "@/q-ui/components/login.vue";
export default defineComponent({
    name: 'SelectableItem',
    computed: {
        login() {
            return login
        }
    },
    components: {},
    props: {
        itemText: String,
        itemTitle: String,
        isSelected: Boolean,
        itemId: Number,
        loginType: String
    },
    data() {
        return {
            itemText: this.itemText,
            isSelected: this.isSelected,
            itemId: this.itemId,
            itemTitle: this.itemTitle,
            LoginOption: LoginIdentifier
        }
    },
    async created() {},
    methods: {
        toggleSelection() {
            this.$emit('emitUiClickMetric', this.itemId)
            this.$emit('toggle', this.itemId)
        },
        toggleSelectionOnFocus() {
            this.$emit('toggle', this.itemId)
        }
    },
})
</script>

<style scoped lang="scss">
.item-container {
    padding: 15px;
    display: flex;
    align-items: center;
    height: 38px;
}

.selected {
    user-select: none;
}

.item-title {
    font-weight: bold;
    margin-bottom: 2px;
}

.text {
    display: flex;
    flex-direction: column;
    font-size: 15px;
}

.icon {
    margin-right: 15px;
}

/* Theme specific styles */
body.jb-dark {
    .item-container {
        border: 1px solid white;
    }
    .selected {
        border: 1px solid #29a7ff;
    }
    .icon > svg > path {
        fill: white;
    }
}

body.jb-light {
    .item-container {
        border: 1px solid black;
    }
    .selected {
        border: 1px solid #3574f0;
    }
    .icon > svg > path {
        fill: black;
    }
}
</style>
