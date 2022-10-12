<template>
    <div>
        <div class="nav-tabs-navigation">
            <div class="nav-tabs-wrapper">
                <slot class="nav" name="nav"></slot>
            </div>
        </div>
        <div class="content">
            <slot name="content"></slot>
        </div>
    </div>
</template>

<script>
export default {
  name: "tabs-layout"
};
</script>

<style>
    .nav-tabs-wrapper{
        height: 240px
    }
</style>