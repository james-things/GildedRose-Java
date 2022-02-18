package com.gildedrose;

class GildedRose {
    private Item[] items;
    private boolean isExpired;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    //processes all items and updates their quality using the appropriate method for the given item type
    public void UpdateQuality() {
        for (Item item : items) {
            isExpired = CheckExpiration(item);

            if (item.getAges())
                UpdateAgingQuality(item);
            if (item.getConjured())
                UpdateConjuredQuality(item);
            if (!item.getAges() && !item.getLegendary() && !item.getConjured())
                UpdateNormalQuality(item);

            item.ApplyQualityLimits();

            if (!item.getLegendary())
                DecrementSellIn(item);
        }
    }

    //update the quality of an item which ages
    private void UpdateAgingQuality(Item item) {
        if (item.getSellIn() > 10)
            item.setQuality(item.getQuality() + 1);
        if (item.getSellIn() <= 10 && item.getSellIn() > 5)
            item.setQuality(item.getQuality() + 2);
        if (item.getSellIn() <= 5 && item.getSellIn() > 0)
            item.setQuality(item.getQuality() + 3);
        if (isExpired)
            item.setQuality(0);
    }

    //update the quality of a conjured item
    private void UpdateConjuredQuality(Item item) {
        item.setQuality(item.getQuality() - ((isExpired) ? 4 : 2));
    }

    //update the quality of a normal item
    private void UpdateNormalQuality(Item item) {
        item.setQuality(item.getQuality() - ((isExpired) ? 2 : 1));
    }

    //reduce sellIn by 1
    private void DecrementSellIn(Item item) {
        item.setSellIn(item.getSellIn() - 1);
    }

    //reduce quality to zero if not legendary and expired, returns boolean isExpired
    private boolean CheckExpiration(Item item) {
        if (item.getSellIn() <= 0 && !item.getLegendary())
            item.setQuality(0);
        return (item.getQuality() <= 0);
    }

    //return list of items
    public Item[] getItems() {
        return this.items;
    }

    //return item with specified index
    public Item getItem(int index) {
        return this.items[index];
    }
}
